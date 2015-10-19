package com.crm.guard.filter;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;

import java.util.ArrayList;
import java.util.List;

public class CriteriaAliasBuilder {

    private Criterias aliases = new Criterias();

    public String build(Criteria criteria, String path) {
        String property;
        if (!path.contains(".")) {
            property = path;
            return property;
        }

        String[] split = path.split("\\.");
        property = "";

        List<String> paths = new ArrayList<String>();
        List<String> aliasNames = new ArrayList<String>();

        for (int i = 0; i < split.length - 1; i++) {
            if (i == 0) {
                paths.add(split[i]);
                aliasNames.add(split[i]);
            } else {
                paths.add(paths.get(i-1) + "." + split[i]);
                aliasNames.add(split[i-1] + "." + split[i]);
            }
        }

        for (int i = 0; i < split.length - 1; i++) {
            if (!this.aliases.contains(paths.get(i))) {
                String aliasName = RandomStringUtils.randomAlphabetic(3);
                String createAliasName = StringUtils.isEmpty(property) ? aliasNames.get(i) : property + "." + split[i];
                criteria.createAlias(createAliasName, aliasName);
                aliases.add(new Criteriaa(paths.get(i), aliasName));
                property = aliasName;

            } else {
                property = aliases.get(paths.get(i)).getName();
            }
        }
        property += "." + split[split.length - 1];
        return property;
    }

    private class Criterias extends ArrayList<Criteriaa> {
        public boolean contains(String path) {
            for (Criteriaa criteriaa : this) {
                if (criteriaa.getPath().equals(path)) {
                    return true;
                }
            }
            return false;
        }

        public Criteriaa get(String path) {
            for (Criteriaa criteriaa : this) {
                if (criteriaa.getPath().equals(path)) {
                    return criteriaa;
                }
            }
            return null;
        }
    }

    private class Criteriaa {
        private String path;
        private String name;

        private Criteriaa(String path, String name) {
            this.path = path;
            this.name = name;
        }

        public String getPath() {
            return path;
        }

        public String getName() {
            return name;
        }

    }
}
