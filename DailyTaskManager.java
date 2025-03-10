import java.util.Scanner;
import java.util.Stack;
import java.util.LinkedList;

public class DailyTaskManager {
    static String[] tasks = {"Hadiri kuliah", "Latihan coding", "Kerja figma", "Database", "Calculus"};
    static boolean[] selesai = new boolean[tasks.length];       // Status penyelesaian tugas
    static Stack<String> stack = new Stack<>();                 // Stack untuk menyimpan tugas yang telah selesai
    static Stack<String[]> undoStack = new Stack<>();           // Stack untuk menyimpan tugas sebelum diperbarui (Undo)
    static LinkedList<String> list = new LinkedList<>();        // LinkedList untuk menyimpan tugas tambahan
    static boolean isLoadingDisplayed = false;                  // Variabel untuk memastikan animasi loading hanya ditampilkan sekali

    // Menampilkan daftar tugas dengan warna status (hijau untuk selesai, merah untuk belum selesai)
    public static void tampilkanTugas() {
        System.out.println("\u001B[36m===== DAFTAR TUGAS =====\u001B[0m");
        for (int i = 0; i < tasks.length; i++) {
            String statusColor = selesai[i] ? "\u001B[32m" : "\u001B[31m"; // Hijau jika selesai, merah jika belum
            System.out.println(statusColor + (i + 1) + ". " + tasks[i] + "\u001B[0m");
        }
    }    

    public static void main(String[] args) throws InterruptedException {
        Scanner input = new Scanner(System.in);
        int pilihan;

        // Menampilkan animasi kucing hanya sekali
        if (!isLoadingDisplayed) {
            loading(); 
            isLoadingDisplayed = true;
        }

        // Loop utama untuk menu
        do {
            tampilkanMenuUtama();
            System.out.print("\u001B[33mPilih Menu: \u001B[0m");

            // Validasi input agar hanya angka yang diterima
            while (!input.hasNextInt()) {
                System.out.println("\u001B[31mInput harus berupa angka!\u001B[0m");
                input.next();
            }
            pilihan = input.nextInt();
            input.nextLine();

            // Menjalankan menu sesuai pilihan
            switch (pilihan) {
                case 1:
                    arrayTaskManager(input);
                    break;
                case 2:
                    linkedListTaskManager(input);
                    break;
                case 3:
                    keluarProgram();
                    break;
                default:
                    System.out.println("\u001B[31mPilihan tidak valid!\u001B[0m");
            }
        } while (pilihan != 3);
    }

    // Menampilkan menu utama
    public static void tampilkanMenuUtama() {
        System.out.println("\u001B[36m===== MAIN MENU =====\u001B[0m");
        System.out.println("\u001B[34m1. Array Task Manager\u001B[0m");
        System.out.println("\u001B[34m2. Linked List Task Manager\u001B[0m");
        System.out.println("\u001B[34m3. Exit\u001B[0m");
    }

    // Mengelola tugas menggunakan array
    public static void arrayTaskManager(Scanner input) {
        int pilihan;
        do {
            System.out.println("\u001B[36m===== ARRAY TASK MANAGER =====\u001B[0m");
            System.out.println("\u001B[34m1. Lihat Tugas\u001B[0m");
            System.out.println("\u001B[34m2. Perbarui Tugas\u001B[0m");
            System.out.println("\u001B[34m3. Tandai Tugas Selesai\u001B[0m");
            System.out.println("\u001B[34m4. Batalkan Penyelesaian Tugas\u001B[0m");
            System.out.println("\u001B[34m5. Undo Perubahan\u001B[0m");
            System.out.println("\u001B[34m0. Kembali ke Menu Utama\u001B[0m");
            System.out.print("\u001B[33mPilih Menu: \u001B[0m");

            while (!input.hasNextInt()) {
                System.out.println("\u001B[31mInput harus berupa angka!\u001B[0m");
                input.next();
            }
            pilihan = input.nextInt();
            input.nextLine();

            switch (pilihan) {
                case 1:
                    tampilkanTugas();
                    break;
                case 2:
                    System.out.print("Masukkan indeks tugas: ");
                    if (input.hasNextInt()) {
                        int index = input.nextInt();
                        input.nextLine();
                        if (index > 0 && index <= tasks.length) {
                            undoStack.push(tasks.clone());
                            System.out.print("Masukkan tugas baru: ");
                            tasks[index - 1] = input.nextLine();
                            selesai[index - 1] = false;
                            System.out.println("\u001B[32mTugas berhasil diperbarui!\u001B[0m");
                        } else {
                            System.out.println("\u001B[31mIndeks tidak valid!\u001B[0m");
                        }
                    }
                    break;
                case 3:
                    tampilkanTugas();
                    System.out.print("Masukkan indeks tugas yang selesai: ");
                    if (input.hasNextInt()) {
                        int selesaiIndex = input.nextInt();
                        input.nextLine();
                        if (selesaiIndex > 0 && selesaiIndex <= tasks.length) {
                            stack.push(tasks[selesaiIndex - 1]);
                            selesai[selesaiIndex - 1] = true;
                            System.out.println("\u001B[32mTugas ditandai selesai!\u001B[0m");
                        } else {
                            System.out.println("\u001B[31mIndeks tidak valid!\u001B[0m");
                        }
                    }
                    break;
                case 4:
                    if (!stack.isEmpty()) {
                        String batal = stack.pop();
                        for (int i = 0; i < tasks.length; i++) {
                            if (tasks[i].equals(batal)) {
                                selesai[i] = false;
                                break;
                            }
                        }
                        System.out.println("\u001B[33mMembatalkan: " + batal + "\u001B[0m");
                    } else {
                        System.out.println("\u001B[31mStack kosong!\u001B[0m");
                    }
                    break;
                case 5:
                    if (!undoStack.isEmpty()) {
                        tasks = undoStack.pop();
                        System.out.println("\u001B[33mPerubahan terakhir dibatalkan!\u001B[0m");
                    } else {
                        System.out.println("\u001B[31mTidak ada perubahan yang bisa dibatalkan!\u001B[0m");
                    }
                    break;
            }
        } while (pilihan != 0);
    }

    // Mengelola tugas menggunakan Linked List
    public static void linkedListTaskManager(Scanner input) {
        int pilihan;
        do {
            System.out.println("\u001B[36m===== LINKED LIST TASK MANAGER =====\u001B[0m");
            System.out.println("\u001B[34m1. Tambahkan Tugas Baru\u001B[0m");
            System.out.println("\u001B[34m2. Hapus Tugas\u001B[0m");
            System.out.println("\u001B[34m3. Tampilkan Semua Tugas\u001B[0m");
            System.out.println("\u001B[34m0. Kembali ke Menu Utama\u001B[0m");
            System.out.print("\u001B[33mPilih Menu: \u001B[0m");

            while (!input.hasNextInt()) {
                System.out.println("\u001B[31mInput harus berupa angka!\u001B[0m");
                input.next();
            }
            pilihan = input.nextInt();
            input.nextLine();

            switch (pilihan) {
                case 1:
                    System.out.print("Masukkan tugas baru: ");
                    String tugasLinked = input.nextLine();
                    if (!tugasLinked.isEmpty()) {
                        list.add(tugasLinked);
                        System.out.println("\u001B[32mTugas ditambahkan!\u001B[0m");
                    }
                    break;
                case 2:
                    System.out.print("Masukkan tugas yang ingin dihapus: ");
                    String hapusTugas = input.nextLine();
                    if (list.remove(hapusTugas)) {
                        System.out.println("\u001B[33mTugas \"" + hapusTugas + "\" berhasil dihapus!\u001B[0m");
                    }
                    break;
                case 3:
                    System.out.println("\u001B[34mTugas dalam Linked List: " + list + "\u001B[0m");
                    break;
            }
        } while (pilihan != 0);
    }

    // Menampilkan animasi kucing saat loading
    public static void loading() {
        System.out.println("\u001B[35m  /\\_/\\  (\"\n" +
                           " ( o.o )  \"\n" +
                           " > ^_^ <  Meow! Siap menyelesaikan tugasmu hari ini?\"\u001B[0m");
    }

    // Menampilkan pesan keluar dari aplikasi
    public static void keluarProgram() {
        System.out.println("\u001B[35m");
        System.out.println("   \\^_^/  Zzz");
        System.out.println("   ( o.o )");
        System.out.println("   > ^ <   Meow... Tidur dulu ya, sampai jumpa lagi!");
        System.out.println("\u001B[0m");
        System.out.println("\u001B[35mMeskipun tugas sudah selesai, jangan lupa untuk kembali lagi! ✨");
        System.out.println("\u001B[34mTerima kasih telah menggunakan aplikasi Daily Task Manager!\u001B[0m");
    }
}
