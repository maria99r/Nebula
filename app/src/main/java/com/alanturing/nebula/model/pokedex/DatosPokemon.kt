package com.alanturing.nebula.model.pokedex

data class NextEvolution(
    val num: Int,
    val name: String
)

data class DatosPokemon(
    val id: Int,
    val num: Int,
    val name: String,
    val img: String,
    val type: List<String>,
    val height: String,
    val weight: String,
    val candy: String,
    val candy_count: Int,
    val egg : String,
    val spawn_chance: Float,
    val avg_spawns: Float,
    val spawn_time: String,
    val multipliers: List<Float>,
    val weaknesses: List<String>,
    val prev_evolution: List<NextEvolution>,
    val next_evolution: List<NextEvolution>
    )
