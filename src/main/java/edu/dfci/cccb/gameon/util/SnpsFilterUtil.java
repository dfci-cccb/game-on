package edu.dfci.cccb.gameon.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SnpsFilterUtil {
  public static String test () {
    return "fffff";
  }

  public static List<String> getBuilds ()
  {
    List<String> list = new ArrayList<String> ();
    list.add ("GRCh36/hg18");
    return list;
  }

  public static List<String> getStrands () {
    List<String> list = new ArrayList<String> ();
    list.add ("+");
    return list;
  }

  public static List<String> getNStudies () {
    List<String> list = new ArrayList<String> ();
    list.add ("1");
    list.add ("2");
    list.add ("3");
    list.add ("4");
    return list;
  }

  public static List<String> getEffectAllele () {
    List<String> list = new ArrayList<String> ();
    list.add ("A");
    list.add ("C");
    list.add ("G");
    return list;
  }

  public static List<String> getRefAllele () {
    List<String> list = new ArrayList<String> ();
    list.add ("C");
    list.add ("T");
    list.add ("G");
    return list;
  }

  public static List<String> getChromosomes () {
    String[] chrs = new String[] { "chr1",
                                  "chr2",
                                  "chr3",
                                  "chr4",
                                  "chr5",
                                  "chr6",
                                  "chr7",
                                  "chr8",
                                  "chr10",
                                  "chr22",
                                  "chr11",
                                  "chr12",
                                  "chr14",
                                  "chr20",
                                  "chr16",
                                  "chr17",
                                  "chr18",
                                  "chr13",
                                  "chr9",
                                  "chr15",
                                  "chr19",
                                  "chrX",
                                  "chr21" };
    Arrays.sort (chrs);
    return Arrays.asList (chrs);
  }
}
