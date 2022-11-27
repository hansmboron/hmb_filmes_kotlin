package br.com.cinema.ui.buy_ticket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import br.com.cinema.R

class BuyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy)

        val action = supportActionBar
        action!!.title = "Comprar Ingresso"

        val movieTitle: String = intent.getStringExtra("title") ?: "Filme"
        val etTitle = findViewById<EditText>(R.id.et_title)
        etTitle.setText(movieTitle)
        findViewById<EditText>(R.id.et_valor).setText("R$19,99")

        val buyBtn = findViewById<Button>(R.id.btn_buy)
        buyBtn.setOnClickListener(View.OnClickListener {
            val cartao = findViewById<EditText>(R.id.et_credito)

            if (cartao.text != null && cartao.text.length < 9) {
                cartao.error = "Número Inválido"
                Toast.makeText( this, "Existem Campo(s) Inválido(s)!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText( this, "Ingresso comprado com sucesso!", Toast.LENGTH_LONG).show()
                this.finish()
            }
        })
    }
}