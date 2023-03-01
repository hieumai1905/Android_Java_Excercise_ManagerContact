package com.example.sqlite.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.sqlite.R;
import com.example.sqlite.activity.MainActivity;
import com.example.sqlite.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends BaseAdapter {
    List<Contact> listContact;
    public static List<Contact> listChecked;

    public Adapter() {
    }

    public Adapter(List<Contact> listContact) {
        this.listContact = listContact;
        listChecked = new ArrayList<>();
    }

    public List<Contact> getListChecked() {
        return listChecked;
    }

    @Override
    public int getCount() {
        return listContact.size();
    }

    @Override
    public Object getItem(int i) {
        return listContact.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listContact.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // các tham số lần lượt là vị trí của item, view của item, viewgroup chứa item
        // phương thức này có tác dụng tạo ra view cho item
        // view ở đây là phần tử listView, nếu view != null thì không cần tạo mới
        // view này được sử dụng lại, chỉ việc cập nhật nội dung mới.
        // nếu null thì tạo mới
        View viewContact = view;
        if (view == null) {
            viewContact = View.inflate(viewGroup.getContext(), R.layout.contact_item, null);
        }

        // bind du lieu vao phan tu view
        Contact contact = (Contact) getItem(i);
        ((TextView) viewContact.findViewById(R.id.txtName)).setText(contact.getName());
        ((TextView) viewContact.findViewById(R.id.txtPhone)).setText(contact.getPhone());
        CheckBox ckbChecked = viewContact.findViewById(R.id.ckbSelect);
        ckbChecked.setChecked(false);
        ckbChecked.setOnCheckedChangeListener((compoundButton, check) -> {
            if (check) {
                listChecked.add(contact);
            } else {
                listChecked.remove(contact);
            }
            if (listChecked.isEmpty()) {
                MainActivity.btnDeleteContact.setEnabled(false);
                MainActivity.btnDeleteContact.setBackground(MainActivity.btnDeleteContact.getContext().getResources().getDrawable(R.color.disable));
            } else {
                MainActivity.btnDeleteContact.setEnabled(true);
                MainActivity.btnDeleteContact.setBackground(MainActivity.btnDeleteContact.getContext().getResources().getDrawable(R.color.enable));
            }
        });
        return viewContact;
    }

    public void clearListChecked() {
        MainActivity.btnDeleteContact.setEnabled(false);
        MainActivity.btnDeleteContact.setBackground(MainActivity.btnDeleteContact.getContext().getResources().getDrawable(R.color.disable));
        listChecked.clear();
    }
}