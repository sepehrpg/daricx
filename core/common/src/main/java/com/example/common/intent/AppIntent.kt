package com.example.common.intent

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri



object AppIntent {

    fun modularIntent(activity: Activity, context: Context, packageName:String, finishActivity:Boolean){
        val intent = Intent(context,Class.forName(packageName))
        context.startActivity(intent)
        if (finishActivity){
            activity.finish()
        }
    }


    fun intentAndShareTextPlain(context: Context,subject:String,shareMessage:String) {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            context.startActivity(Intent.createChooser(shareIntent, "choose one"))
        } catch (e: Exception) {
            //e.printStackTrace()
        }
    }


    fun intentToSms(context:Context,phoneNumber:String,message:String){
        // Create an intent to open the SMS app
        val smsIntent = Intent(Intent.ACTION_VIEW)
        smsIntent.data = Uri.parse("sms:$phoneNumber")
        smsIntent.putExtra("sms_body", message)

        // Start the SMS app
        context.startActivity(smsIntent)
    }


    fun intentToUrl(context:Context,url:String){
        val browserIntent =  Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(browserIntent)
    }


    fun intentToAllNavigationApplication(content:Context, latitude: String?, longitude: String?) {
        val mapIntent: Intent = Uri.parse(
            //"geo:$latitude,$longitude?z=14"
            "google.navigation:q=$latitude,$longitude"
        ).let { location ->
            // Or map point based on latitude/longitude
            // val location: Uri = Uri.parse("geo:37.422219,-122.08364?z=14") // z param is zoom level
            Intent(Intent.ACTION_VIEW, location)
        }
        content.startActivity(mapIntent)
    }
    /*fun intentToGoogleMap(content:Context, latitude: String?, longitude: String?) {
        //BEST FOR NAVIGATION
        *//*val lat1="35.731155028639265"
        val lat2="51.15142117994009"
        val uri ="http://maps.google.com/maps?f=d&hl=en&saddr=$latitude,$longitude&daddr=$lat1,$lat2"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        content.startActivity(Intent.createChooser(intent, "Select an application"))*//*

        // Create a Uri from an intent string. Open map using intent to pin a specific location (latitude, longitude)
        val mapUri = Uri.parse("https://maps.google.com/maps/search/$latitude,$longitude")
        val intent = Intent(Intent.ACTION_VIEW, mapUri)
        content.startActivity(intent)

        *//*val gmmIntentUri = Uri.parse("geo:$latitude,$longitude")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        content.startActivity(mapIntent)*//*
    }*/


    fun directionFromCurrentMap(content:Context,destinationLatitude: String, destinationLongitude: String) {
        // Create a Uri from an intent string. Open map using intent to show direction from current location (latitude, longitude) to specific location (latitude, longitude)
        val mapUri = Uri.parse("https://maps.google.com/maps?daddr=$destinationLatitude,$destinationLongitude")
        val intent = Intent(Intent.ACTION_VIEW, mapUri)
        content.startActivity(intent)
    }

    fun directionBetweenTwoMap(content:Context,sourceLatitude: String, sourceLongitude: String, destinationLatitude: String, destinationLongitude: String) {
        // Create a Uri from an intent string. Open map using intent to show direction between two specific locations
        val mapUri = Uri.parse("https://maps.google.com/maps?saddr=$sourceLatitude,$sourceLongitude&daddr=$destinationLatitude,$destinationLongitude")
        val intent = Intent(Intent.ACTION_VIEW, mapUri)
        content.startActivity(intent)
    }
}