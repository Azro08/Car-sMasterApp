package com.chugay.cartech.sharedpref

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.chugay.cartech.ui.shared.login.LoginActivity


class MySharedPreferences(context: Context) {
    var context : Context? = context
    var sharedPref : SharedPreferences? = null

    init {
        sharedPref = context.getSharedPreferences("myPref", Context.MODE_PRIVATE)
    }

    fun saveMasters(added: Int){
        var editor = sharedPref!!.edit()
        editor.putInt("masters_exist", added)
        editor.commit()
    }

    fun mastersExist() : Int{
        return sharedPref!!.getInt("masters_exist", 0)
    }

    fun saveAdmin(added : Int){
        var editor = sharedPref!!.edit()
        editor.putInt("admin_exists", added)
        editor.commit()
    }

    fun adminExists() : Int
    {
        return sharedPref!!.getInt("admin_exists", 0)
    }

    fun saveLanguage(language: String){
        var editor = sharedPref!!.edit()
        editor.putString("language", language)
        editor.commit()
    }

    fun getLanguage() : String?
    {
        return sharedPref!!.getString("language", "en")
    }

    fun saveEmail(email: String){
        var editor = sharedPref!!.edit()
        editor.putString("email", email)
        editor.commit()
    }

    fun isFirstTimeLoad(activity : Activity){
        val email = sharedPref!!.getString("email","empty")
        if (email.equals("empty")){
            val intent = Intent(context, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context!!.startActivity(intent)
            activity.finish()
        }
    }

    fun getEmail() : String?
    {
        return sharedPref!!.getString("email", "empty")
    }

    fun saveUserType(type : String){
        var editor = sharedPref!!.edit()
        editor.putString("user_type", type)
        editor.commit()
    }

    fun getLoggedInBy() : String? {
        return sharedPref!!.getString("user_type", "empty")
    }

    fun removeUserEmail(){
        var editor = sharedPref!!.edit()
        editor.remove("email")
        editor.clear()
        editor.commit()
    }

}
