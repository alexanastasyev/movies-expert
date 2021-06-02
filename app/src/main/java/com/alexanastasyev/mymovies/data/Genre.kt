package com.alexanastasyev.mymovies.data

@Suppress("unused")
enum class Genre(val id: Int, val title: String) {
    ACTION(28, "боевик"),
    ADVENTURE(12, "приключения"),
    ANIMATION(16, "мультфильм"),
    COMEDY(35, "комедия"),
    CRIMINAL(80, "криминал"),
    DOCUMENTARY(99, "документальный"),
    DRAMA(18, "драма"),
    FAMILY(10751, "семейный"),
    FANTASY(14, "фэнтези"),
    HISTORY(36, "история"),
    HORROR(27, "ужасы"),
    MUSIC(10402, "музыка"),
    MYSTERY(9648, "детектив"),
    ROMANCE(10749, "мелодрама"),
    SCIENCE_FICTION(878, "фантастика"),
    TV_MOVIE(10770, "телевизионный фильм"),
    THRILLER(53, "триллер"),
    WAR(10752, "военный"),
    WESTERN(37, "вестерн"),

    NULL_GENRE(0, "");

    companion object {
        fun getById(id: Int): Genre {
            for (genre in values()) {
                if (genre.id == id) {
                    return genre
                }
            }
            return NULL_GENRE
        }
    }
}