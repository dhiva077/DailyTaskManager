import java.util.Scanner;
import java.util.Stack;
import java.util.LinkedList;

public class DailyTaskManager {
    static String[] tasks = {"Hadiri kuliah", "Latihan coding", "Kerja figma", "Database", "Calculus"};
    static boolean[] selesai = new boolean[tasks.length];
    static Stack<String> stack = new Stack<>();
    static Stack<String[]> undoStack = new Stack<>();
    static LinkedList<String> list = new LinkedList<>();
    static boolean isLoadingDisplayed = false;

    public static void main(String[] args) throws InterruptedException {
        Scanner input = new Scanner(System.in);
        int pilihan;

        if (!isLoadingDisplayed) {
            loading(); // Menampilkan animasi kucing hanya sekali
            isLoadingDisplayed = true;
        }

        tampilkanMenu(); // Menampilkan menu setelah animasi kucing

        do {
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
                            undoStack.push(tasks.clone());  // Push salinan tasks ke undoStack
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
                    System.out.print("Masukkan tugas baru: ");
                    String tugasLinked = input.nextLine();
                    if (!tugasLinked.isEmpty()) {
                        list.add(tugasLinked);
                        System.out.println("\u001B[32mTugas ditambahkan!\u001B[0m");
                    } else {
                        System.out.println("\u001B[31mTugas tidak boleh kosong!\u001B[0m");
                    }
                    break;
                case 6:
                    System.out.print("Masukkan tugas yang ingin dihapus: ");
                    String hapusTugas = input.nextLine();
                    if (list.remove(hapusTugas)) {
                        System.out.println("\u001B[33mTugas \"" + hapusTugas + "\" berhasil dihapus!\u001B[0m");
                    } else {
                        System.out.println("\u001B[31mTugas tidak ditemukan!\u001B[0m");
                    }
                    break;
                case 7:
                    tampilkanTugas();
                    break;
                case 8:
                    if (!undoStack.isEmpty()) {
                        tasks = undoStack.pop();  // Mengembalikan tugas ke keadaan sebelumnya
                        System.out.println("\u001B[33mPerubahan terakhir dibatalkan!\u001B[0m");
                    } else {
                        System.out.println("\u001B[31mTidak ada perubahan yang bisa dibatalkan!\u001B[0m");
                    }
                    break;
                case 0:
                    keluarProgram(); // Menampilkan pesan menarik saat keluar
                    break;
                default:
                    System.out.println("\u001B[31mPilihan tidak valid!\u001B[0m");
            }

            if (pilihan != 0) {
                tampilkanMenu();  // Menampilkan menu setelah setiap pilihan
            }

        } while (pilihan != 0);
    }

    public static void tampilkanMenu() throws InterruptedException {
        System.out.println("\u001B[36m===== Daily Task Manager =====\u001B[0m");
        System.out.println("\u001B[34m1. Lihat Tugas\u001B[0m");
        System.out.println("\u001B[34m2. Perbarui Tugas\u001B[0m");
        System.out.println("\u001B[34m3. Tandai Tugas Selesai\u001B[0m");
        System.out.println("\u001B[34m4. Batalkan Penyelesaian Tugas\u001B[0m");
        System.out.println("\u001B[34m5. Tambahkan Tugas Baru\u001B[0m");
        System.out.println("\u001B[34m6. Hapus Tugas\u001B[0m");
        System.out.println("\u001B[34m7. Tampilkan Semua Tugas\u001B[0m");
        System.out.println("\u001B[34m8. Undo Perubahan\u001B[0m");
        System.out.println("\u001B[34m0. Keluar\u001B[0m");
    }

    public static void tampilkanTugas() {
        System.out.println("\n\u001B[34m=== Daftar Tugas ===\u001B[0m");
        for (int i = 0; i < tasks.length; i++) {
            String warna = selesai[i] ? "\u001B[32m" : "\u001B[31m";
            System.out.println(warna + (i + 1) + ". " + tasks[i] + "\u001B[0m");
        }
    }

    public static void loading() {
        System.out.println(
            "\u001B[35m  /\\_/\\  (\"\n" +
            " \u001B[35m( o.o )  \"\n" +
            " \u001B[35m> ^_^ <  Meow! Siap menyelesaikan tugasmu hari ini?\"\u001B[0m"
        );
    }

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