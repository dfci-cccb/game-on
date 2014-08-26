/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 */

package edu.dfci.cccb.gameon.domain;

import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.NaN;
import static java.lang.Double.POSITIVE_INFINITY;
import static java.lang.Double.parseDouble;
import static java.lang.System.*;
import static java.sql.DriverManager.getConnection;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.AbstractMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvMapReader;
import org.supercsv.prefs.CsvPreference.Builder;
import org.supercsv.util.CsvContext;

/**
 * @author levk
 */
public class TsvToH2Importer {

  private static final String INPUT_FILE_PATH = getProperty ("game-on.input", "~/game-on.tsv");
  private static final String OUTPUT_DB_PATH = getProperty ("game-on.output", "~/game-on");
  private static final String GENE_INFO_DB_PATH = getProperty ("game-on.geneinfo", "~/game-on-info");

  public static void main (String[] args) throws Exception {
    Class.forName ("org.h2.Driver");
    CsvMapReader reader = null;
    try {
      reader = new CsvMapReader (new FileReader (new File (INPUT_FILE_PATH)),
                                 new Builder ('"', '\t', "\n\r").build ());

      Statement gene = gene ().createStatement ();
      Statement out = out ().createStatement ();

      String[] header = reader.getHeader (true);
      CellProcessor value = new CellProcessor () {

        @Override
        public Object execute (Object value, CsvContext context) {
          if (".".equals (value))
            return 0;
          if ("NA".equals (value) || "null".equals (value) || "NaN".equals (value))
            return NaN;
          else if ("-Inf".equals (value))
            return NEGATIVE_INFINITY;
          else if ("Inf".equals (value))
            return POSITIVE_INFINITY;
          else
            return parseDouble (value.toString ());
        }
      };
      CellProcessor[] processors =
                                   new CellProcessor[] {
                                                        null, null, null, null, null, null, null, null, value, value,
                                                        value, value, value, value, value, null, null, null, value,
                                                        value, value, value, value, value };
      long count = 1;
      System.out.println ("Starting import");
      long time = currentTimeMillis ();
      for (Map<String, Object> snp; (snp = exist (reader.read (header, processors))) != null; count++) {
        if (count % 100000 == 0) {
          System.out.println ("Processed 100K entries in " + (currentTimeMillis () - time) + "ms");
          time = currentTimeMillis ();
        }
        insert (snp, out, gene, count);
      }

      System.out.println ("done");
    } finally {
      if (reader != null)
        reader.close ();
    }
  }

  private static Connection gene () throws Exception {
    return getConnection ("jdbc:h2:file:" + GENE_INFO_DB_PATH, "sa", "");
  }

  private static void insert (Map<String, Object> snp, Statement out, Statement gene, long count) throws Exception {
    ResultSet rs = gene.executeQuery ("select gene from rs_gene_import where rs_number like '"
                                      + snp.get ("rs_number").toString () + "'");
    String info = null, symbol = null;
    if (rs.first ()) {
      info = symbol = rs.getString ("gene");
      //System.out.println ("info=" + info + " symbol=" + symbol);
    }
    out.execute ("insert into snp values (" + count + ","
                 + val (snp.get ("beta")) + ","
                 + "'" + snp.get ("Build") + "',"
                 + "'" + chromosome (snp.get ("coordinate")) + "',"
                 + coordinate (snp.get ("coordinate")) + ","
                 + val (snp.get ("eaf_uk")) + ","
                 + "'" + snp.get ("effect_allele") + "',"
                 + val (snp.get ("L_CI")) + ","
                 + "'" + snp.get ("markername") + "',"
                 + "'" + snp.get ("nstudy") + "',"
                 + val (snp.get ("OR")) + ","
                 + val (snp.get ("pvalue")) + ","
                 + "'" + snp.get ("ref_allele") + "',"
                 + "'" + snp.get ("rs_number") + "',"
                 + val (snp.get ("se")) + ","
                 + "'" + snp.get ("strand") + "',"
                 + val (snp.get ("U_CI")) + ","
                 + "'" + symbol + "'," // gene_symbol
                 + "'" + info + "'," // gene_info
                 + "'" + snp.get ("nstudy_erneg") + "',"
                 + "'" + snp.get ("effect_allele_erneg") + "',"
                 + "'" + snp.get ("ref_allele_erneg") + "',"
                 + val (snp.get ("beta_erneg")) + ","
                 + val (snp.get ("se_erneg")) + ","
                 + val (snp.get ("OR_erneg")) + ","
                 + val (snp.get ("L_CI_erneg")) + ","
                 + val (snp.get ("U_CI_erneg")) + ","
                 + val (snp.get ("pvalue_erneg")) + ")");
  }

  private static Connection out () throws Exception {
    Connection out = getConnection ("jdbc:h2:file:" + OUTPUT_DB_PATH, "sa", "");
    out.createStatement ().execute ("create table snp(id bigint primary key,"
                                    + "betaValue double,"
                                    + "build varchar(255),"
                                    + "chromosome varchar(255),"
                                    + "coordinate bigint,"
                                    + "eaf_uk_value double,"
                                    + "effect_allele varchar(255),"
                                    + "l_ci_value double,"
                                    + "marker_name varchar(255),"
                                    + "n_study varchar(255),"
                                    + "or_value double,"
                                    + "p_value double,"
                                    + "ref_allele varchar(255),"
                                    + "rs_number varchar(255),"
                                    + "se_value double,"
                                    + "strand varchar(255),"
                                    + "u_ci_value double,"
                                    + "gene_symbol varchar(255),"
                                    + "gene_info varchar(255),"
                                    + "n_study_erneg varchar(255),"
                                    + "effect_allele_erneg varchar(255),"
                                    + "ref_allele_erneg varchar(255),"
                                    + "betaValue_erneg double,"
                                    + "se_value_erneg double,"
                                    + "or_value_erneg double,"
                                    + "l_ci_value_erneg double,"
                                    + "u_ci_value_erneg double,"
                                    + "p_value_erneg double)");
    return out;
  }

  private static String coordinate (Object coordinate) {
    return String.valueOf (Long.parseLong (coordinate.toString ().substring (1 + coordinate.toString ().indexOf (':'))));
  }

  private static String chromosome (Object coordinate) {
    return coordinate.toString ().substring (0, coordinate.toString ().indexOf (':'));
  }

  private static String val (Object o) {
    double v = Double.valueOf (o.toString ());
    if (Double.isNaN (v))
      return "sqrt(-1)";
    else if (Double.isInfinite (v)) {
      if (v > 0)
        return "POWER(0, -1)";
      else
        return "(-POWER(0, -1))";
    }
    else
      return String.valueOf (v);
  }

  private static Map<String, Object> exist (final Map<String, Object> map) {
    return map == null ? null : new AbstractMap<String, Object> () {

      @Override
      public Set<Entry<String, Object>> entrySet () {
        return map.entrySet ();
      }

      @Override
      public Object get (Object key) {
        Object result = map.get (key);
        if (result == null)
          throw new NoSuchElementException ("No element " + key);
        else
          return result;
      }
    };
  }
}
