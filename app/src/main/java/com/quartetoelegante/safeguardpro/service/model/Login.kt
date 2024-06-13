package com.quartetoelegante.safeguardpro.service.model

object Login {
    var userId = 0
    var userCpf = ""
    var userAdm = false

    fun userConected(id: Int, cpf: String, adm: Boolean) {
        userId = id
        userCpf = cpf
        userAdm = adm
    }
}