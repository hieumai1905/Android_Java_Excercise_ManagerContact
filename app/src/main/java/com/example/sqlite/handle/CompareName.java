package com.example.sqlite.handle;

import com.example.sqlite.model.Contact;

public class CompareName implements ComparatorContact{
    @Override
    public int compare(Object o, Object t1) {
        Contact contact1 = (Contact)o;
        Contact contact2 = (Contact)t1;
        String name1 = contact1.getName().substring(contact1.getName().lastIndexOf(" ") + 1);
        String name2 = contact1.getName().substring(contact2.getName().lastIndexOf(" ") + 1);
        return name1.compareTo(name2);
    }
}
