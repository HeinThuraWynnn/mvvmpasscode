package com.anubhav87.mvvmtutorial.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.anubhav87.mvvmtutorial.R
import com.anubhav87.mvvmtutorial.SharedPreference
import kotlinx.android.synthetic.main.activity_add_note.*
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class AddNoteActivity : AppCompatActivity() {
    var id=0
    val AES = "AES"
    var encTitle: String = ""
    var encDesc: String = ""
    var decTitle: String = ""
    var decDesc: String = ""
    var enc_dec: Boolean = false

    companion object {
        const val EXTRA_TITLE = "com.anubhav87.mvvmtutorial.EXTRA_TITLE"
        const val EXTRA_DESCRIPTION = "com.anubhav87.mvvmtutorial.EXTRA_DESCRIPTION"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.save_note -> {
                saveNote()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveNote() {
        if (edit_text_title.text.toString().trim().isBlank() || edit_text_description.text.toString().trim().isBlank()) {
            Toast.makeText(this, "Can not insert empty note!", Toast.LENGTH_SHORT).show()
            return
        }

        val data = Intent().apply {
            putExtra(EXTRA_TITLE, edit_text_title.text.toString())
            putExtra(EXTRA_DESCRIPTION, edit_text_description.text.toString())
        }

        setResult(Activity.RESULT_OK, data)
        finish()
    }
    fun btnEnc(view: View){
        val sharedPreference = SharedPreference(this)
//        val password = sharedPreference.getValueString("code")
        val password = "1234"
        encDesc  = encrypt(edit_text_description.text.toString(),password.toString())
        edit_text_description.setText(encDesc)
        enc_dec = true
        Toast.makeText(this,"Encrypted",Toast.LENGTH_LONG).show()
    }
    fun btnDec(view: View){

        val sharedPreference = SharedPreference(this)
//        val password = sharedPreference.getValueString("code")
        val password = "1234"
        decDesc  = decrypt(edit_text_description.text.toString(),password.toString())
        edit_text_description.setText(decDesc)
        enc_dec = false
        Toast.makeText(this,"Decrypted",Toast.LENGTH_LONG).show()

    }

    @Throws(Exception::class)
    private fun decrypt(outputString: String, password: String): String {
        val key = generateKey(password)
        val c = Cipher.getInstance(AES)
        c.init(Cipher.DECRYPT_MODE, key)
        val decodedValue = Base64.decode(outputString, Base64.DEFAULT)
        val decValue = c.doFinal(decodedValue)
        return String(decValue)
    }

    @Throws(Exception::class)
    private fun encrypt(Data: String, password: String): String {
        val key = generateKey(password)
        val c = Cipher.getInstance(AES)
        c.init(Cipher.ENCRYPT_MODE, key)
        val encVal = c.doFinal(Data.toByteArray())
        return Base64.encodeToString(encVal, Base64.DEFAULT)
    }

    @Throws(Exception::class)
    private fun generateKey(password: String): SecretKeySpec {
        val digest = MessageDigest.getInstance("SHA-256")
        val bytes = password.toByteArray(charset("UTF-8"))
        digest.update(bytes, 0, bytes.size)
        val key = digest.digest()
        return SecretKeySpec(key, "AES")
    }

    fun showCreateCategoryDialog() {
        val context = this
        val builder = AlertDialog.Builder(context)
        builder.setTitle("New Category")

        // https://stackoverflow.com/questions/10695103/creating-custom-alertdialog-what-is-the-root-view
        // Seems ok to inflate view with null rootView
        val view = layoutInflater.inflate(R.layout.activity_add_note, null)

        val categoryEditText = view.findViewById(R.id.categoryEditText) as EditText

        builder.setView(view);

        // set up the ok button
        builder.setPositiveButton(android.R.string.ok) { dialog, p1 ->
            val newCategory = categoryEditText.text
            var isValid = true
            if (newCategory.isBlank()) {
                categoryEditText.error = getString(R.string.validation_empty)
                isValid = false
            }

            if (isValid) {
                // do something
            }

            if (isValid) {
                dialog.dismiss()
            }
        }

        builder.setNegativeButton(android.R.string.cancel) { dialog, p1 ->
            dialog.cancel()
        }

        builder.show();
    }
}

