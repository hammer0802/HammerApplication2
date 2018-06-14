package com.example.takanori.hammerapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson





/**
 * Created by Takanori on 2018/03/03.
 */
class MyRecyclerAdaptor(val activity:ListActivity):RecyclerView.Adapter<MyRecyclerViewHolder>() {
    private val preference: SharedPreferences by lazy { activity.getSharedPreferences("kazu", Context.MODE_PRIVATE) }
        val gson = Gson()
    val list: MutableList<Item> by lazy { gson.fromJson<MutableList<Item>>(preference!!.getString("list", ""), object : TypeToken<MutableList<Item>>() {}.type) }        //Jsonの設定


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyRecyclerViewHolder {

        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.list_item, parent,false)
        return MyRecyclerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
}

    override fun onBindViewHolder(holder: MyRecyclerViewHolder?, position: Int) {
        holder!!.v.findViewById<TextView>(R.id.item_name).text=list[position].name
        holder!!.v.findViewById<TextView>(R.id.item_number).text=list[position].number.toString()
        //sharedpriferenceで保存されているpositionごとのcounter数値を、リストの右端に表示させる
        holder.v.setOnClickListener{v ->
            val intent1= Intent(activity,MainActivity::class.java)
            intent1.putExtra("position",position)
            activity.startActivity(intent1)
        }
    }
}

class MyRecyclerViewHolder(val v:View):RecyclerView.ViewHolder(v)
