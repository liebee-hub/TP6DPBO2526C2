# TP6DPBO2526C2

## Janji
Saya Refan Maher Aysel dengan NIM 2509129 mengerjakan Tugas Praktikum 6 dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahan-Nya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

## Desain Program

Program memakai pola **MVC sederhana**:

| Class | Peran |
|---|---|
| `App` | Entry point. Membuat `JFrame`, menampilkan `MainMenu`, lalu berpindah ke game saat tombol Mulai ditekan. |
| `MainMenu` (`JPanel`) | Main Menu dengan 2 tombol: **Mulai Permainan** dan **Keluar**. |
| `Player` | Model burung: posisi, ukuran, gambar, `velocityY`, dan hitbox (`getBounds()`). |
| `Pipe` | Model pipa: posisi, ukuran, gambar, `velocityX`, flag `passed`, dan `getBounds()`. |
| `Logic` | Controller. Menyimpan state (skor, game over), game loop `Timer`, `pipesCooldown`, gerak, deteksi tabrakan, skor, restart, dan `KeyListener`. |
| `View` (`JPanel`) | Tampilan. Menggambar player, pipa, overlay Game Over, dan `JLabel` skor. |
| `SoundPlayer` | Utility audio (BGM + sound effect), untuk bonus. |

Atribut utama `Logic`: `score`, `gameOver`, `pipes` (`ArrayList<Pipe>`), `gameLoop` (60 FPS), `pipesCooldown` (1500 ms), `gravity = 1`, `pipeVelocityX = -2`.

## Penjelasan Alur

1. **Program dijalankan**: `App` menampilkan **Main Menu**.
2. **Keluar** memanggil `System.exit(0)`. **Mulai Permainan** membuat `Logic` dan `View`, mengganti isi window, lalu `logic.start()` menjalankan `gameLoop`, `pipesCooldown`, dan BGM.
3. **Setiap tick (±16 ms)**, `Logic.actionPerformed()`:
   - `move()` menerapkan gravitasi pada player, menggeser semua pipa ke kiri, menghapus pipa di luar layar, dan **menambah skor +1** saat pipa atas sepenuhnya melewati player (flag `passed`; pipa bawah diberi `passed = true` sejak dibuat agar skor tidak terhitung dua kali).
   - `checkGameOver()` bernilai true jika hitbox player **bersinggungan dengan pipa** (`Rectangle.intersects`) atau **menyentuh batas bawah window**.
   - `view.repaint()` menggambar ulang tampilan.
4. **Setiap 1500 ms**, `placePipes()` membuat sepasang pipa (atas dan bawah) dengan posisi Y acak dan celah `frameHeight / 4`.
5. **Input**: `SPACE` atau `↑` membuat player melompat (`velocityY = -10`).
6. **Game Over**: `endGame()` menghentikan kedua Timer dan BGM, memutar efek `hit`, lalu `View` menampilkan overlay "GAME OVER" dan skor akhir.
7. **Restart**: tekan **R** saat game over. Posisi player, pipa, dan skor di-reset, lalu game berjalan lagi.

## Daftar Fitur

- [x] Game Over saat menabrak pipa atau jatuh ke bawah
- [x] Restart dengan tombol **R**
- [x] `JLabel` skor
- [x] Skor +1 tiap melewati pipa
- [x] Main Menu (Mulai dan Keluar)
- [x] Bonus: BGM dan sound effect (flap, point, hit)

## Dokumentasi

Video dan gambar program yang dijalankan ada di folder [`Dokumentasi`](./Dokumentasi).

| Main Menu | Gameplay | Game Over |
|---|---|---|
| ![menu](Dokumentasi/menu.png) | ![play](Dokumentasi/gameplay.png) | ![over](Dokumentasi/gameover.png) |
