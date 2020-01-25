package com.example.agenda

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.agenda.db.Contato
import com.example.agenda.db.ContatoRepository
import kotlinx.android.synthetic.main.activity_contato.*
import java.text.SimpleDateFormat
import java.util.*

class ContatoActivity : AppCompatActivity() {

    var cal = Calendar.getInstance()
    var contato: Contato? = null
    var datanascimento: Button? = null

    // private var imgContato: ImageView? = null
    // private var txtNome: EditText? = null
    //private var txtEndereco: EditText? = null
    //private var txtTelefone: EditText? = null
    //private var txtSite: EditText? = null
    // private var btnCadastro: Button? = null
    //private var txtEmail: EditText? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contato)

        val myChildToolbar = toolbar_child
        setSupportActionBar(myChildToolbar)

        // Get a support ActionBar corresponding to this toolbar
        val ab = supportActionBar
        // Enable the Up button
        ab!!.setDisplayHomeAsUpEnabled(true)

        if (intent?.getSerializableExtra("contato") != null) {
            contato = intent?.getSerializableExtra("contato") as Contato
            txtNome?.setText(contato?.nome)
            txtEndereco?.setText(contato?.endereco)
            txtTelefone?.setText(contato?.telefone)
            //dataNascimento = cal.timeInMillis
            txtEmail?.setText(contato?.email)
            txtSite?.setText(contato?.email)
        }else{
            contato = Contato()
        }

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(
                view: DatePicker, year: Int, monthOfYear: Int,
                dayOfMonth: Int
            ) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        datanascimento = txtDatanascimento
        datanascimento!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(
                    this@ContatoActivity,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        })

        btnCadastro?.setOnClickListener {
            contato?.nome = txtNome?.text.toString()
            contato?.endereco = txtEndereco?.text.toString()
            contato?.telefone = txtTelefone?.text.toString()
            contato?.dataNascimento = cal.timeInMillis.toString()
            contato?.email = txtEmail?.text.toString()
            contato?.site = txtSite?.text.toString()

            if(contato?.id?.toInt() == 0){
                ContatoRepository(this).create(contato!!)
            }else{
                ContatoRepository(this).update(contato!!)
            }
            finish()
        }

    }

    private fun updateDateInView() {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        datanascimento!!.text = sdf.format(cal.getTime())
    }

    override fun onResume() {
        super.onResume()
        val intent = intent
        if (intent != null) {
            if (intent.getSerializableExtra("contato") != null) {
                contato = intent.getSerializableExtra("contato") as Contato

                txtNome?.setText(contato?.nome)
                txtEndereco?.setText(contato?.endereco)
                txtTelefone.setText(contato?.telefone.toString())

                txtEmail.setText(contato?.email)
                txtSite?.setText(contato?.site)
                datanascimento?.setText(contato?.dataNascimento)
            } else {
                contato = Contato()
            }
        }

    }
}


