package com.quartetoelegante.safeguardpro.service.model

data class Entrega(
    var id: Int = 0,
    var data: String = "",
    var tempo_uso: String = "",
    var validade: String = "",
    var nome: String = "",
    var idEpi: Int = 0,
    var idFuncionario: Int = 0
)
