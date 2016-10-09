package com.example.cameraapp.modle;

import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 2016/6/15.
 */
public class User
{

    /**
     * last_name : Ramos
     * id : 1
     * first_name : Roger
     * gender : Male
     * ip_address : 194.52.112.37
     * email : rramos0@gizmodo.com
     */

    private String last_name;
    private int id;
    private String first_name;
    private String gender;
    private String ip_address;
    private String email;

    public static User objectFromData(String str, String key)
    {

        try
        {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), User.class);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public String getLast_name()
    {
        return last_name;
    }

    public void setLast_name(String last_name)
    {
        this.last_name = last_name;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getFirst_name()
    {
        return first_name;
    }

    public void setFirst_name(String first_name)
    {
        this.first_name = first_name;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public String getIp_address()
    {
        return ip_address;
    }

    public void setIp_address(String ip_address)
    {
        this.ip_address = ip_address;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
}
