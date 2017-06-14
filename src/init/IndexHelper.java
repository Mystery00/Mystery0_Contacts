package init;

import java.util.ArrayList;
import java.util.List;

import classes.Contact;

public class IndexHelper
{
    private List<Contact> list = new ArrayList<>();

    public void addContact(Contact contact)
    {
        list.add(contact);
    }

    public void removeContact(Contact contact)
    {
        list.remove(contact);
    }

    public List<Contact> getList()
    {
        return list;
    }
}
