package com.example.SpringReactor;
public class Classic {
    private String id;
  
    // name will act as value
    private Object name;
  
    // create curstuctor for reference
    public Classic(String id, Object name)
    {
  
        // assign the value of id and name
        this.id = id;
        this.name = name;
    }
  
    // return private variable id
    public String getId()
    {
        return id;
    }
  
    // return private variable name
    public Object getName()
    {
        return name;
    }
}

