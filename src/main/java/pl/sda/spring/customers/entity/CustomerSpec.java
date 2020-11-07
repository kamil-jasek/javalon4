package pl.sda.spring.customers.entity;

import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

final class CustomerSpec {

    static class CompanyQuery {
        private String name;
        private String vatId;

        CompanyQuery(String name, String vatId) {
            this.name = name;
            this.vatId = vatId;
        }

        public String getName() {
            return name;
        }

        public String getVatId() {
            return vatId;
        }
    }

    static Specification<Customer> withQuery(CompanyQuery query) {
        return (root, cq, cb) -> {
            // select * from customers
            // 1. where 1=1 and name like :name
            // 2. where 1=1 and vatNumber like :number
            // 3. where 1=1 and name like :name and vatNumber like :number
            // 4. where 1=1

            // where 1=1
            final var company = cb.treat(root, Company.class);
            var predicate = cb.conjunction();

            // where 1=1 and name like :name%
            if (StringUtils.hasText(query.name)) {
                final var namePredicate = cb.like(company.get("name"), query.name + "%");
                predicate = cb.and(predicate, namePredicate);
            }

            // where 1=1 and vatNumber like :vatId
            // where 1=1 and name like :name and vatNumber like :vatId
            if (StringUtils.hasText(query.vatId)) {
                final var vatPredicate = cb.like(company.get("vatNumber"), query.vatId + "%");
                predicate = cb.and(predicate, vatPredicate);
            }

            return predicate;
        };
    }
}
