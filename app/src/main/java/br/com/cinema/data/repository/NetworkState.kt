package br.com.cinema.data.repository

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED
}

class NetworkState(val status: Status, val msg: String) {

    companion object {
        val LOADED: NetworkState
        val LOADING: NetworkState
        val ERROR: NetworkState
        val ENDOFLIST: NetworkState


        init {
            LOADED = NetworkState(Status.SUCCESS, "Sucesso")
            LOADING = NetworkState(Status.RUNNING, "Executando")
            ERROR = NetworkState(Status.FAILED, "Algo deu errado")
            ENDOFLIST = NetworkState(Status.FAILED, "Você chegou ao final")
        }
    }
}