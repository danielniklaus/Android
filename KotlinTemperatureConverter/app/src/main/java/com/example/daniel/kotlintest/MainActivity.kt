package com.example.daniel.kotlintest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {


    //penulisan kelas diawali dengan keyword 'class' dilanjutakn nama kelasnya
    //untuk melakukan extend dan implement dari kelas menggunakan ':'
    private val TAG: String = "MainActivityTAG"
    // val untuk nilai konstan atau read only;
    private var arrayUnitTemperature: Array<String>? = null;
    // var untuk nilai yang dapat di ubah,
    // '?' -> untuk mengeksekusi sebuah variable apabila nilainya non null saja,
    // jadi lebih bagus untuk mencegah Null Pointer Exception
    // '!!' -> untuk mengeksekusi sebuah variable apabila nilanya non null maupun null
    private var temperature: String = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        //penulisan method atau function harus menggunakan keyword 'fun' kemudian dilanjutkan
        //dengan nama method atu function nya.
        //dan apabila method atau function tersebut empunya return value maka, setelah tanda tutup kurung
        //diberi tanda titik dua, dan dilanjutkan dengan tipe datanya;
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initComponent();
    }

    private fun initComponent() {
        //mendeklarasikan sebuah array dengan nilai string C F K R Re
        arrayUnitTemperature = arrayOf("C", "F", "K", "R", "Re")

        //mendeklarasikan kepanjangan dari setiap singkatansuhu yang di deklarasikan sebelumnya
        val arrayUnitDescribeTemperature = arrayOf("Celcius", "Fahrenheit", "Kelvin", "Rankine", "Reaumur")

        //mendeklarasikan adapter untuk spiner
        val arrayAdapterUnitTemperature = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, arrayUnitTemperature);
        spinner_from.adapter = arrayAdapterUnitTemperature
        spinner_to.adapter = arrayAdapterUnitTemperature
        spinner_to.setSelection(1)
        tv_unit_temperature_from.text = "Fahrenheit"

        //deklarasi listener onclick ke semua button
        btn_keypad_0.setOnClickListener(this)
        btn_keypad_1.setOnClickListener(this)
        btn_keypad_2.setOnClickListener(this)
        btn_keypad_3.setOnClickListener(this)
        btn_keypad_4.setOnClickListener(this)
        btn_keypad_5.setOnClickListener(this)
        btn_keypad_6.setOnClickListener(this)
        btn_keypad_7.setOnClickListener(this)
        btn_keypad_8.setOnClickListener(this)
        btn_keypad_9.setOnClickListener(this)
        btn_keypad_backspace.setOnClickListener(this)
        btn_keypad_dot.setOnClickListener(this)
        btn_keypad_ac.setOnClickListener(this)
        btn_keypad_plus_minus.setOnClickListener(this)

        spinner_from.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                tv_unit_temperature_from.text = arrayUnitDescribeTemperature.get(position)
                if (temperature != "0" && temperature != "0.0") {
                    calculateTemoeratureConversion()
                }
            }

        }
        spinner_to.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                tv_unit_temperature_to.text = arrayUnitDescribeTemperature.get(position)
                if (temperature != "0" && temperature != "0.0") {
                    calculateTemoeratureConversion()
                }
            }

        }

    }

    //method ini berfungsi untuk melakukan proses konversi dari suatu suhu tertentu menjadi suhu lainnya
    private fun calculateTemoeratureConversion() {
        val unitTemperatureFrom = spinner_from.selectedItem.toString()
        val unitTemperatureTo = spinner_to.selectedItem.toString()
        Log.d(TAG, "unitTemperatureTo:$unitTemperatureTo")
        val temperatureFrom = temperature.toDouble()
        var temperatureTo: Double = 0.0

        if (unitTemperatureFrom == "C") {
            when (unitTemperatureTo) {
                "F" -> {
                    // C x 1,8 + 32
                    temperatureTo = temperatureFrom.times(1.8).plus(32)
                    Log.d(TAG, "temperatureTo new: $temperatureTo")
                }
                "K" -> {
                    // C + 273,15
                    temperatureTo = temperatureFrom.plus(273.15)
                }
                "R" -> {
                    // 1,8 x (C + 496,67)
                    temperatureTo = (temperatureFrom.plus(496.67)).times(1.8)
                }
                "Re" -> {
                    // C x 0,8
                    temperatureTo = temperatureFrom.times(0.8)
                }
                else -> {
                    temperatureTo = temperatureFrom
                }
            }
        } else if (unitTemperatureFrom == "F") {
            when (unitTemperatureTo) {
                "C" -> {
                    // (F - 32) / 1,8
                    temperatureTo = (temperatureFrom.minus(32)).div(1.8)
                }
                "K" -> {
                    // (F + 459,67) / 1,8
                    temperatureTo = (temperatureFrom.plus(459.67)).div(1.8)
                }
                "R" -> {
                    // F + 459,67
                    temperatureTo = temperatureFrom.plus(459.67)
                }
                "Re" -> {
                    // (F - 32) / 2,25
                    temperatureTo = (temperatureFrom.minus(32)).div(2.25)
                }
                else -> {
                    temperatureTo = temperatureFrom
                }
            }
        } else if (unitTemperatureFrom == "K") {
            when (unitTemperatureTo) {
                "C" -> {
                    // K - 273,15
                    temperatureTo = temperatureFrom.minus(273.15)
                }
                "F" -> {
                    // K x 1,8 - 459,67
                    temperatureTo = temperatureFrom.times(1.8).minus(459.67)
                }
                "R" -> {
                    // K x 1,8
                    temperatureTo = temperatureFrom.times(1.8)
                }
                "Re" -> {
                    // (K - 273,15) x 0,8
                    temperatureTo = (temperatureFrom.minus(273.15)).div(0.8)
                }
                else -> {
                    temperatureTo = temperatureFrom
                }
            }
        } else if (unitTemperatureFrom == "R") {
            when (unitTemperatureTo) {
                "C" -> {
                    // R / 1,8 + 273,15
                    temperatureTo = temperatureFrom.div(1.8).plus(273.15)
                }
                "F" -> {
                    // R - 459,67
                    temperatureTo = temperatureFrom.minus(459.67)
                }
                "K" -> {
                    // R / 1,8
                    temperatureTo = temperatureFrom.div(1.8)
                }
                "Re" -> {
                    // (R / 1,8 + 273,15) x 0,8
                    temperatureTo = temperatureFrom.div(1.8).plus(273.15).times(0.8)
                }
                else -> {
                    // nothing to do in here
                    temperatureTo = temperatureFrom
                }
            }
        } else if (unitTemperatureFrom == "Re") {
            when (unitTemperatureTo) {
                "C" -> {
                    // Re / 0,8
                    temperatureTo = temperatureFrom.div(0.8)
                }
                "F" -> {
                    // Re x 2,25 + 32
                    temperatureTo = temperatureFrom.times(2.25).plus(32)
                }
                "K" -> {
                    // Re / 0,8 + 273,15
                    temperatureTo = temperatureFrom.div(0.8).plus(273.15)
                }
                "R" -> {
                    // Re x 2,25 + 491,67
                    temperatureTo = temperatureFrom.times(2.25).plus(491.67)
                }
                else -> {
                    temperatureTo = temperatureFrom
                }
            }
        }
        tv_value_temperature_to.text = temperatureTo.toString()
    }

    override fun onClick(v: View?) {
        //klo di java switch
        when (v?.id) {
        //ini merupakan case
            R.id.btn_keypad_0 -> {
                updateTemperatureValueFrom("0")
            }
            R.id.btn_keypad_1 -> {
                updateTemperatureValueFrom("1")
            }
            R.id.btn_keypad_2 -> {
                updateTemperatureValueFrom("2")
            }
            R.id.btn_keypad_3 -> {
                updateTemperatureValueFrom("3")
            }
            R.id.btn_keypad_4 -> {
                updateTemperatureValueFrom("4")
            }
            R.id.btn_keypad_5 -> {
                updateTemperatureValueFrom("5")
            }
            R.id.btn_keypad_6 -> {
                updateTemperatureValueFrom("6")
            }
            R.id.btn_keypad_7 -> {
                updateTemperatureValueFrom("7")
            }
            R.id.btn_keypad_8 -> {
                updateTemperatureValueFrom("8")
            }
            R.id.btn_keypad_9 -> {
                updateTemperatureValueFrom("9")
            }
            R.id.btn_keypad_backspace -> {
                backspaceTemperatureValueFrom()
            }
            R.id.btn_keypad_ac -> {
                clearTemperatureValueFrom()
            }
            R.id.btn_keypad_dot -> {
                updateTemperatureValueFrom(".")
            }
            R.id.btn_keypad_plus_minus -> {
                changePlusMinusValueFrom()
            }
        //defalut
            else -> {

            }
        }
    }

    //method ini berfungsi untuk mengubah nilai negatif menjadi positif dan sebaliknya
    private fun changePlusMinusValueFrom() {
        if (temperature.startsWith("-")) {
            temperature = temperature.replace("-", "")
        } else {
            temperature = "-$temperature"
        }
        temperature = if (temperature == "-0") "0" else temperature
        tv_value_temperature_from.text = temperature

    }
    //method ini bergungsi untuk meng-set semua nilai di view kembali ke semula atau nol
    private fun clearTemperatureValueFrom() {
        temperature = "0"
        tv_value_temperature_from.text = temperature
        tv_value_temperature_to.text = "0"
    }

    //method ini berfungis untuk menghapus karakter pada nilai yang mau dikonversi karena, pada
    //app inputan dilakukan menggunakan button angka, yang tersedia bukan menggukan soft keyboard
    private fun backspaceTemperatureValueFrom() {
        if (temperature != "0") {
            temperature = temperature.substring(0, temperature.length - 1)
        }
        temperature = if (temperature.isEmpty() || temperature == "-") "0" else temperature
        tv_value_temperature_from.text = temperature
        if (temperature == "0" || temperature == "0.0") {
            tv_value_temperature_to.text = "0"
        }

    }

    //method in iberfungsi untuk melakukan update atau eksekusi lakukan proses onversi suhu
//    apabila nilainya berubah karena, pada app tidak ada butten sama dengan jadi , proses koncersi berjalan di
//    listener
    private fun updateTemperatureValueFrom(strValue: String) {
        if (strValue == "0") {
            temperature = if (temperature == "0") "0" else temperature.plus(strValue)
        } else if (strValue == ".") {
            temperature = if (!temperature.contains(".")) temperature.plus(strValue) else temperature
        } else {
            temperature = if (temperature == "0") strValue else temperature.plus(strValue)
        }
        Log.d(TAG, "temperature: $temperature")
        tv_value_temperature_from.text = temperature
        if (temperature != "0" && temperature != "0.0") {
            calculateTemoeratureConversion()
        } else {
            tv_value_temperature_to.text = "0"
        }
    }


    override fun onDestroy() {
        super.onDestroy()
    }
}
