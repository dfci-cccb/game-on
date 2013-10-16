package edu.dfci.cccb.gameon.domain;
import ch.lambdaj.Lambda;
import static ch.lambdaj.Lambda.convert;
import static ch.lambdaj.Lambda.join;
import ch.lambdaj.function.convert.Converter;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import static java.util.Arrays.asList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import static java.util.concurrent.TimeUnit.DAYS;
import java.util.concurrent.atomic.AtomicInteger;
import javax.persistence.TypedQuery;
import org.apache.log4j.Logger;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", table = "SNP", schema = "PUBLIC")
@RooDbManaged(automaticallyDelete = true)
public class Snp {

    private static final Logger log = Logger.getLogger(Snp.class);

    private static final int MAXIMUM_QUERIES = 50000;

    private static final long DURATION = 1;

    private static final TimeUnit DURATION_UNIT = DAYS;

    private static final LoadingCache<UserDetails, AtomicInteger> users = CacheBuilder.newBuilder().expireAfterWrite(DURATION, DURATION_UNIT).build(new CacheLoader<UserDetails, AtomicInteger>() {

        public AtomicInteger load(UserDetails key) {
            return new AtomicInteger(1);
        }
    });

    private static final LoadingCache<String, List<String>> distinctValues = CacheBuilder.newBuilder().build(new CacheLoader<String, List<String>>() {

        @Override
        public List<String> load(String key) throws Exception {
            String query = "SELECT DISTINCT " + key.toUpperCase() + " FROM Snp";
            return Snp.entityManager().createQuery(query, String.class).getResultList();
        }
    });

    private static final Map<String, String> formats = new HashMap<String, String>() {

        private static final long serialVersionUID = 1L;

        {
            put("build", "o.build = :%s");
            put("strand", "o.strand = :%s");
            put("NStudy", "o.nStudy = :%s");
            put("effectAllele", "o.effectAllele = :%s");
            put("refAllele", "o.refAllele = :%s");
            put("markerName", "o.markerName LIKE LOWER(:%s)");
            put("chromosome", "o.chromosome LIKE LOWER(:%s)");
            put("coordinateUpper", "o.coordinate <= CONVERT(:%s, BIGINT)");
            put("coordinateLower", "o.coordinate >= CONVERT(:%s, BIGINT)");
        }
    };

    public static List<java.lang.String> getDistinctValues(String column) {
        try {
            return distinctValues.get(column.toLowerCase());
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static TypedQuery<Snp> findGeneric(Map<String, String[]> searchTerms) throws QueryLimitExceededException {
        try {
            if (users.get((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).incrementAndGet() > MAXIMUM_QUERIES) throw new QueryLimitExceededException();
            Map<String, Object> queryParams = new HashMap<String, Object>();
            String query = "SELECT o FROM Snp AS o" + formatWhereClause(searchTerms, queryParams);
            log.debug("findGeneric query=\"" + query + "\" with parameters " + queryParams);
            TypedQuery<Snp> q = Snp.entityManager().createQuery(query, Snp.class);
            for (Entry<String, Object> queryParam : queryParams.entrySet()) q.setParameter(queryParam.getKey(), queryParam.getValue());
            return q;
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static long countGeneric(Map<String, String[]> searchTerms) {
        Map<String, Object> queryParams = new HashMap<String, Object>();
        String query = "SELECT COUNT(o) FROM Snp AS o" + formatWhereClause(searchTerms, queryParams);
        log.debug("countGeneric query=\"" + query + "\" with parameters " + queryParams);
        TypedQuery<Long> q = Snp.entityManager().createQuery(query, Long.class);
        for (Entry<String, Object> queryParam : queryParams.entrySet()) q.setParameter(queryParam.getKey(), queryParam.getValue());
        return q.getSingleResult();
    }

    private static String formatWhereClause(Map<String, String[]> searchTerms, final Map<String, Object> queryParams) {
        final int[] count = new int[] { 0 };
        String whereClause = join(convert(searchTerms.entrySet(), new Converter<Map.Entry<String, String[]>, String>() {

            @Override
            public String convert(final Entry<String, String[]> from) {
                final String column = from.getKey();
                return "(" + join(Lambda.convert(asList(from.getValue()), new Converter<String, String>() {

                    @Override
                    public String convert(String from) {
                        return format(column, from);
                    }
                }), " OR ") + ")";
            }

            private String format(String column, Object value) {
                String paramName = column + (count[0]++);
                queryParams.put(paramName, value);
                return String.format(formats.get(column), paramName);
            }
        }), " AND ");
        return whereClause.length() < 1 ? "" : " WHERE " + whereClause;
    }
}
