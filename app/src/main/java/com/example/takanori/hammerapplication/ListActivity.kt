package com.example.takanori.hammerapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : AppCompatActivity() {
    private val preference: SharedPreferences by lazy { getSharedPreferences("kazu", Context.MODE_PRIVATE) }
    val gson = Gson()
    val list: MutableList<Item> by lazy { gson?.fromJson<MutableList<Item>>(preference!!.getString("list", ""), object : TypeToken<MutableList<Item>>() {}.type) ?: mutableListOf<Item>()}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        create_button.setOnClickListener(android.view.View.OnClickListener {
            val intent1= Intent(this, MainActivity::class.java)
            val l= list?.size ?: 0
            intent1.putExtra("position",l)
            list += Item()

            val e = preference!!.edit()
            e.putString("list", gson.toJson(list)) //gson.toJson(list)でlistのデータをjson形式で渡す。listは28行目で要素が追加されているため、新しいlistのデータが入る。
            e.commit()
            this.startActivity(intent1)
        })

    }

    override fun onStart() {
        super.onStart()
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
    }

    override fun onResume() {
        super.onResume()
        var recyclerAdaptor = MyRecyclerAdaptor(this)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = recyclerAdaptor
        recyclerAdaptor.reload()
        recycler_view.adapter.notifyDataSetChanged()
    }

    override fun onPostResume() {
        super.onPostResume()
    }

    override fun onRestart() {
        super.onRestart()
    }
}
