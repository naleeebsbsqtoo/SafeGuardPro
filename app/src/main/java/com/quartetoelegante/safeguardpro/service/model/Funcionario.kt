package com.quartetoelegante.safeguardpro.service.model

data class Funcionario(
    var id: Int = 0,
    var nome: String = "",
    var cpf: String = "",
    var senha: String = "",
    var admin: Boolean = false
)
