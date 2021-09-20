package eu.example.save_with_sharedpreferences

import android.content.Context
// import android.content.SharedPreferences ---- NOT SURE IF NEEDED ?
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import eu.example.save_with_sharedpreferences.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    // Just a test to close keyboard when a btn is pressed
    /* hide soft keyboard after writing and sending message or any */
    private fun closeKeyBoard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)

        // creating a sharePref object
        // Name parameter -
        // Need to specify unique name. We can have several different ones, but must specify which one to save or load too -
        // when interacting whit it.
        // Mode parameter, we can specify private then only this app can access the sharedPref object -
        // We could also use, public, so other apps can access it -
        // or mode append, which means it will not overwrite preferences but will append new ones to those
        val sharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val editor = sharedPref.edit() // using the editor with our sharedPref object ??

        //
        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString()
            // must add plugin kotlin-android-extensions in gradle to call it directly without binding
            val age = etAge.text.toString().toInt()
            val isAdult = cbAdult.isChecked


            // all date we use with sharedPreferences is a key - value pair
            editor.apply {

                // putString function takes a name as string and a key. Here using the val we just created name, and age for values
                // key name, value the val name - Note we could give it any name we wanted for the key
                putString("name", name) // key name, value the val name
                putInt("age", age)// key age, value the val age
                putBoolean("adultOrNot", isAdult) // here using another name string for the key, just as an example
                apply() // put the data into the object
            }

            // Just for user info
            Toast.makeText(this, "Data saved", Toast.LENGTH_LONG).show()

            closeKeyBoard()

            // test close keyboard

        }

        // read the data from sharedPref, and set it to the editText + checkbox ( etName + etAge + cbAdult)
        btnLoad.setOnClickListener {
            val name = sharedPref.getString("name", null) // read the key and if not exist a default value null
            val age = sharedPref.getInt("age", 0) // read the key and if not exist a default as zero
            val isAdult = sharedPref.getBoolean("adultOrNot", false ) // set it to false if don't exist

            // set the values i read in back to the views and checkbox for display to user
            etName.setText(name)
            etAge.setText(age.toString())
            cbAdult.isChecked = isAdult
        }
    }
}