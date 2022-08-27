package org.example.coktail.repository;

public class LazyFilter {

    @Override
    public boolean equals(Object obj) {
        return obj instanceof org.hibernate.collection.internal.PersistentBag;
    }

}
