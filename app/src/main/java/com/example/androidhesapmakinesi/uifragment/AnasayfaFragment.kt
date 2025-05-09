package com.example.androidhesapmakinesi.uifragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.androidhesapmakinesi.R
import com.example.androidhesapmakinesi.databinding.FragmentAnasayfaBinding

class AnasayfaFragment : Fragment() {
    private lateinit var binding: FragmentAnasayfaBinding
    private var ifade = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAnasayfaBinding.inflate(inflater, container, false)

        //Sayi butonlarını tanımlama.
        binding.buttonSayi0.setOnClickListener {
            ifade += "0"
            binding.textViewYaz.text = ifade
        }
        binding.buttonSayi1.setOnClickListener {
            ifade += "1"
            binding.textViewYaz.text = ifade
        }
        binding.buttonSayi2.setOnClickListener {
            ifade += "2"
            binding.textViewYaz.text = ifade
        }
        binding.buttonSayi3.setOnClickListener {
            ifade += "3"
            binding.textViewYaz.text = ifade
        }
        binding.buttonSayi4.setOnClickListener {
            ifade += "4"
            binding.textViewYaz.text = ifade
        }
        binding.buttonSayi5.setOnClickListener {
            ifade += "5"
            binding.textViewYaz.text = ifade
        }
        binding.buttonSayi6.setOnClickListener {
            ifade += "6"
            binding.textViewYaz.text = ifade
        }
        binding.buttonSayi7.setOnClickListener {
            ifade += "7"
            binding.textViewYaz.text = ifade
        }
        binding.buttonSayi8.setOnClickListener {
            ifade += "8"
            binding.textViewYaz.text = ifade
        }
        binding.buttonSayi9.setOnClickListener {
            ifade += "9"
            binding.textViewYaz.text = ifade
        }
        ///////////////////////////////////////////////////////////

        //İşlem butonlarını tanımlama.
        binding.buttonIfadeTopla.setOnClickListener {
            if (ifade.isNotEmpty() && !operatorKontrol()) {
                ifade += "+"
                binding.textViewYaz.text = ifade
            }
        }
        binding.buttonIfadeEksi.setOnClickListener {
            if (ifade.isNotEmpty() && !operatorKontrol()) {
                ifade += "-"
                binding.textViewYaz.text = ifade
            }
        }
        binding.buttonIfadeCarp.setOnClickListener {
            if (ifade.isNotEmpty() && !operatorKontrol()) {
                ifade += "x"
                binding.textViewYaz.text = ifade
            }
        }
        binding.buttonIfadeBolu.setOnClickListener {
            if (ifade.isNotEmpty() && !operatorKontrol()) {
                ifade += "÷"
                binding.textViewYaz.text = ifade
            }
        }
        ///////////////////////////////////////////////////////////

        //Sonuç, Sıfırlama ve Silme butonlarını tanımlama.
        binding.buttonIfadeSonuc.setOnClickListener {
            val sonuc = hesapla()
            binding.textViewYaz.text = sonuc
            ifade = sonuc // sonucu yeni ifadeye çevir
        }

        binding.buttonSifirla.setOnClickListener {
            ifade = ""
            binding.textViewYaz.text = "0"
        }

        binding.buttonSil.setOnClickListener {
            if (ifade.isNotEmpty()) {
                ifade = ifade.dropLast(1) //sondaki eleman silme komutu.
                binding.textViewYaz.text = if (ifade.isEmpty()) "0" else ifade
            }
        }

        return binding.root
    }

    private fun operatorKontrol(): Boolean {
        return ifade.isNotEmpty() && (ifade.last() == '+' || ifade.last() == '-' || ifade.last() == 'x' || ifade.last() == '÷')
    }

    private fun ifadeParcala(): List<String> {
        val liste = mutableListOf<String>()
        var sayi = ""

        for (karakter in ifade) {
            if (karakter.isDigit()) {
                sayi += karakter
            } else {
                if (sayi.isNotEmpty()) {
                    liste.add(sayi)
                    sayi = ""
                }
                liste.add(karakter.toString()) // operatörü ekle
            }
        }
        if (sayi.isNotEmpty()) {
            liste.add(sayi) // en son sayı
        }
        return liste
    }

    private fun hesapla(): String {
        val liste = ifadeParcala().toMutableList()
        // 1. x ve ÷ işlemleri
        var i = 0
        while (i < liste.size) {
            val eleman = liste[i]
            if (eleman == "x" || eleman == "÷") {
                val sol = liste[i - 1].toDouble()
                val sag = liste[i + 1].toDouble()
                val sonuc = if (eleman == "x") sol * sag else sol / sag

                // 3 elemanı silip sonucu ekle
                liste[i - 1] = sonuc.toString()
                liste.removeAt(i) // operatör
                liste.removeAt(i) // sağ operand
                i -= 1 // geriye kay
            } else {
                i++
            }
        }
        // 2. + ve - işlemleri
        i = 0
        while (i < liste.size) {
            val eleman = liste[i]
            if (eleman == "+" || eleman == "-") {
                val sol = liste[i - 1].toDouble()
                val sag = liste[i + 1].toDouble()
                val sonuc = if (eleman == "+") sol + sag else sol - sag

                liste[i - 1] = sonuc.toString()
                liste.removeAt(i)
                liste.removeAt(i)
                i -= 1
            } else {
                i++
            }
        }

        return if (liste.isNotEmpty()) liste[0] else "0"
    }
}