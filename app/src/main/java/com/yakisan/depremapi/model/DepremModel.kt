package com.yakisan.depremapi.model

data class DepremModel(
    val AM: String,
    val Depth: Int,
    val ID: String,
    val LastUpdate: String,
    val Latitude: String,
    val Longitude: String,
    val Magnitude: Double,
    val MagnitudeType: String,
    val MapImage: String,
    val Region: String,
    val Time: String
)