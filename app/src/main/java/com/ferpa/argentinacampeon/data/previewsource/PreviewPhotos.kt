package com.ferpa.argentinacampeon.data.previewsource


import com.ferpa.argentinacampeon.data.remote.dto.PhotoDto
import com.ferpa.argentinacampeon.domain.model.*
import com.ferpa.argentinacampeon.R

object PreviewPhotos {

    val prevPlayerTitleDibu = PlayerTitle(
        id = "9e17ec8c-456e-4682-8f7d-39087cf4f9b6",
        displayName = "Dibu Martínez",
        nationalTeam = "Argentina"
    )

    val prevPlayerTitleMessi = PlayerTitle(
        id = "50ffe7fa-dedf-41a5-b2c0-3b3d07e8bfe1",
        displayName = "Lionel Messi",
        nationalTeam = "Argentina"
    )

    val prevPlayerTitlePaisesBajos = PlayerTitle(
        id = "54ddd1a5-7811-43ef-9621-0065f371bc42",
        displayName = "Virgil van Dijk",
        nationalTeam = "Paises Bajos"
    )

    val prevPhotoTitle = PhotoDto(
        id = "5147d77a-7290-4b2f-a1e1-740c46257dc3",
        photoUrl = "http://192.168.100.4:8080/phototest/prev9.jpg",
        match = MatchTitle(
            id = "",
            title = "Cuartos de final: Paises Bajos vs. Argentina",
            score = "2 - 2 (3 - 4)"
        ),
        players = listOf(
            prevPlayerTitleDibu,
            prevPlayerTitlePaisesBajos
        ),
        photographer = PhotographerTitle(
            id = "6692db68-1293-4140-9a63-3cd9f6dc480a",
            name = "Jose Fotografo"
        ),
        tags = listOf(Tag("3", "epic"), Tag("3", "campeones")),
        rank = 0.0,
        description = null,
        photoType = null,
        momentId = null,
        insertDate = ""
    )


    val prevPhotographer1 = Photographer(id = "photographer1", name = "José Fotografo")
    val prevPhotographer2 = Photographer(id = "photographer2")

    val messi = Player(
        id = "10Arg",
        name = "Lionel Messi",
        nickName = "Leopoldo",
        number = 10
    )

    val dibu = Player(
        id = "23Arg",
        name = "Emiliano Martinez",
        nickName = "Dibu",
        number = 23
    )

    val enzo = Player(
        id = "24Arg",
        name = "Enzo Fernandez",
        number = 24
    )

    val ota = Player(
        id = "19Arg",
        name = "Nicolás Otamendi",
        nickName = "Ota",
        number = 19
    )

    val prevTagList1 = listOf(
        "epic",
        "gol",
        "festejo",
        "cuartos de final",
        "Scaloneta",
        "Atajadón"
    )


    val photoList: List<Int> = listOf(
        R.drawable.prev1,
        R.drawable.prev2,
        R.drawable.prev3,
        R.drawable.prev4,
        R.drawable.prev5,
        R.drawable.prev6,
        R.drawable.prev7,
        R.drawable.prev8,
        R.drawable.prev9,
        R.drawable.prev10,
        R.drawable.prev11
    )

    val prevPlayerMessi = Player(
        id = "50ffe7fa-dedf-41a5-b2c0-3b3d07e8bfe1",
        displayName = "Leo Messi",
        nationalTeam = "Argentina",
        name = "Lionel Andrés Messi",
        nickName = "GOAT",
        birthday = "1987-06-24",
        position = "Delantero",
        team = "PSG",
        profilePhotoUrl = null,
        stats = Stats(
            7,
            minutes = 789,
            goals = 7,
            assists = 3,
            shots = 33,
            passes = 312,
            wrongPasses = null,
            passAccuracy = 88,
            recoveries = 3,
            fouls = 9,
            foulsReceived = 21,
            yellowCards = 1,
            redCards = null
        )
    )

}