package com.example.minishopmanger

import android.content.ContentValues
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listViewProduits = findViewById<ListView>(R.id.listViewProduits)

        // Charger la liste des produits depuis le fichier JSON
        val produits = lireProduitsDepuisJSON()

        // Lier les produits au ListView
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, produits)
        listViewProduits.adapter = adapter

        // Enregistrement dans SQLite lors du clic sur un produit
        listViewProduits.setOnItemClickListener { parent, _, position, _ ->
            val produitNom = parent.getItemAtPosition(position).toString()

            // Insertion dans la base SQLite
            val dbHelper = DBHelper(this)
            val db = dbHelper.writableDatabase
            val values = ContentValues().apply {
                put("produit_nom", produitNom)
                put("quantite", 1)
            }
            db.insert("panier", null, values)
            db.close()

            Toast.makeText(this, "$produitNom ajouté au panier !", Toast.LENGTH_SHORT).show()
        }
    }

    private fun lireProduitsDepuisJSON(): ArrayList<String> {
        val liste = ArrayList<String>()
        val inputStream = assets.open("produits.json")
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        val jsonObject = JSONObject(jsonString)
        val jsonArray = jsonObject.getJSONArray("produits")

        for (i in 0 until jsonArray.length()) {
            val item = jsonArray.getJSONObject(i)
            liste.add(item.getString("nom"))
        }
        return liste
    }
}