package com.example.minishopmanger

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listViewProduits = findViewById<ListView>(R.id.listViewProduits)

        // Récupération de la liste des produits
        val produits = resources.getStringArray(R.array.produits)

        // Liaison des données avec le ListView
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, produits)
        listViewProduits.adapter = adapter

        // Affichage du Toast au clic
        listViewProduits.setOnItemClickListener { parent, _, position, _ ->
            val produitSelectionne = parent.getItemAtPosition(position).toString()
            Toast.makeText(this, "Produit sélectionné : $produitSelectionne", Toast.LENGTH_SHORT).show()
        }
    }
}