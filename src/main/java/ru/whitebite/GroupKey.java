package ru.whitebite;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class GroupKey {
    int column;
    String key;

    public GroupKey(int column, String key) {
        this.column = column;
        this.key = key;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        return this.column == ((GroupKey) obj).column && this.key.equals(((GroupKey) obj).key);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hash = 0;
        int size = key.length();
        for (int i = 0 ; i < size ; i++)
        {
            hash += key.charAt(i)*prime^(size-(i+1));
        }
        //System.out.println("hash: " + hash);
        return hash;
    }

}
