package com.example.takanori.hammerapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.security.InvalidKeyException



class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val preference: SharedPreferences by lazy { getSharedPreferences("kazu", Context.MODE_PRIVATE) }
    //private var posi: Int = 0
    val gson = Gson()
    //val list: MutableList<Item> by lazy { gson.fromJson<MutableList<Item>>(preference!!.getString("list", ""), object : TypeToken<MutableList<Item>>() {}.type) }
    val list: MutableList<Item> by lazy { gson.fromJson<MutableList<Item>>(preference.getString("list", ""), object : TypeToken<MutableList<Item>>() {}.type) ?: mutableListOf<Item>()}
    val intent1: Intent by lazy {this.intent}
    val position: Int by lazy { intent1.getIntExtra("position",0) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        val intent1= this.intent
        val position= intent1?.getIntExtra("position",0) ?: 0
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)


        //posi = intent.getIntExtra("position", 0)

        init()

    }

    fun init() {
        up.setOnClickListener(View.OnClickListener {
            var v = value.text.toString().toInt()
            v++
            value.setText(v.toString())
        })
        down.setOnClickListener(View.OnClickListener {
            var v = value.text.toString().toInt()
            if (v <= 0) {
                throw InvalidKeyException()
            }
            v--
            value.setText(v.toString())
        })

        value.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(text: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (text.isNullOrBlank()) {
                    value.setText("0")
                } else {
                    var v = value.text.toString().toInt()
                    down.isEnabled = v > 0
                    up.isEnabled = v <= 100
                    if (v < 0) {
                        value.setText("0")
                    } else if (v > 100) {
                        value.setText("0")
                    } else if (text.toString() != v.toString()) {
                        value.setText(v.toString())
                    }
                }
                value.setSelection(value.length())
            }
        })
        //value.setText(data!!.getInt(posi.toString()+"kazu", 0).toString())
        //name.setText(data!!.getString(posi.toString()+"namae", ""))
        value.setText(list[position].number.toString())
        name.setText(list[position].name)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
            val e = this.preference.edit()
            //e.putInt(posi.toString()+"kosuu", this.value.text.toString().toInt())
            //e.putString(posi.toString()+"namae", this.name.text.toString())
            value.text
            name.text
            list[position].name = name.text.toString()
            list[position].number = value.text.toString().toInt()
            e.putString("list", gson.toJson(list))
            e.apply()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
