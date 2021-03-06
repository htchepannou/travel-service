package io.tchepannou.k.travel.domain;

import java.io.Serializable;

public abstract class Persistent implements Serializable{
    public abstract Integer getId ();

    public boolean equals(Object that){
        if (that == null || !that.getClass().equals(getClass())){
            return false;
        }

        Integer id = getId();
        if (id == null){
            return super.equals(that);
        } else {
            return id.equals(((Persistent)that).getId());
        }
    }
}
