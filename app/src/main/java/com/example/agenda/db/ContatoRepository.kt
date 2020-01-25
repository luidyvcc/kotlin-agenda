package com.example.agenda.db

import android.content.Context
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.db.update
import org.jetbrains.anko.db.delete
import timber.log.Timber

class ContatoRepository(val context: Context) {

    fun findAll() : ArrayList<Contato> = context.database.use {
        val contatos = ArrayList<Contato>()

        select(ConstantDb.CONTATOS_DB_TABLE, "id", "email", "endereco", "nome", "telefone", "datanascimento", "site", "foto")
            .parseList(object: MapRowParser<List<Contato>> {
                override fun parseRow(columns: Map<String, Any?>): List<Contato> {
                    val id = columns.getValue("id")
                    val email = columns.getValue("email")
                    val endereco = columns.getValue("endereco")
                    val nome = columns.getValue("nome")
                    val telefone = columns.getValue("telefone")
                    val datanascimento = columns.getValue("datanascimento")
                    val site = columns.getValue("site")
                    val foto = columns.getValue("foto")

                    val contato = Contato(
                        id.toString()?.toLong(),
                        foto?.toString(),
                        nome?.toString(),
                        endereco?.toString(),
                        telefone?.toString(),
                        datanascimento?.toString(),
                        email?.toString(),
                        site?.toString())
                    contatos.add(contato)
                    return contatos
                }
            })

        contatos
    }



    fun create(contato: Contato) = context.database.use {
        insert(
            ConstantDb.CONTATOS_DB_TABLE,
            "foto" to contato.foto,
            "nome" to contato.nome,
            "endereco" to contato.endereco,
            "telefone" to contato.telefone,
            "email" to contato.email,
            "site" to contato.site,
            "dataNascimento" to contato.dataNascimento)
    }


    fun update(contato: Contato) = context.database.use {
        val updateResult = update(
            ConstantDb.CONTATOS_DB_TABLE,
            "foto" to contato.foto,
            "nome" to contato.nome,
            "endereco" to contato.endereco,
            "telefone" to contato.telefone,
            "email" to contato.email,
            "site" to contato.site)
            .whereArgs("id = {id}","id" to contato.id).exec()

        Timber.d("Update result code is $updateResult")
    }


    fun delete(id: Long) = context.database.use {
        delete(ConstantDb.CONTATOS_DB_TABLE, "id = {contatoId}", *arrayOf("contatoId" to id))
    }


}
