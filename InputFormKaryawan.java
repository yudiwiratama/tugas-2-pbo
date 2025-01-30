import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InputFormKaryawan extends JFrame {
    private JTextField nikField;
    private JTextField namaField;
    private JTextField alamatField;
    private JTextField tempatLahirField;
    private JTextField tanggalLahirField;
    private JComboBox<String> jenisKelaminComboBox;
    private JTextField teleponField;
    private JComboBox<String> statusPernikahanComboBox;
    private JTextField alamat1Field;
    private JTextField alamat2Field;
    private JButton submitButton;
    private JButton loadButton;
    private JList<String> dataList;
    private DefaultListModel<String> listModel;
    private List<String[]> dataKaryawanList;

    public InputFormKaryawan() {
        setTitle("Form Input Karyawan");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel Input
        JPanel inputPanel = new JPanel(new GridLayout(11, 2));

        // NIK
        inputPanel.add(new JLabel("NIK:"));
        nikField = new JTextField();
        inputPanel.add(nikField);

        // Nama
        inputPanel.add(new JLabel("Nama:"));
        namaField = new JTextField();
        inputPanel.add(namaField);

        // Alamat
        inputPanel.add(new JLabel("Alamat:"));
        alamatField = new JTextField();
        inputPanel.add(alamatField);

        // Tempat Lahir
        inputPanel.add(new JLabel("Tempat Lahir:"));
        tempatLahirField = new JTextField();
        inputPanel.add(tempatLahirField);

        // Tanggal Lahir
        inputPanel.add(new JLabel("Tanggal Lahir:"));
        tanggalLahirField = new JTextField();
        inputPanel.add(tanggalLahirField);

        // Jenis Kelamin
        inputPanel.add(new JLabel("Jenis Kelamin:"));
        jenisKelaminComboBox = new JComboBox<>(new String[]{"Laki-laki", "Perempuan"});
        inputPanel.add(jenisKelaminComboBox);

        // Telepon
        inputPanel.add(new JLabel("Telepon:"));
        teleponField = new JTextField();
        inputPanel.add(teleponField);

        // Status Pernikahan
        inputPanel.add(new JLabel("Status Pernikahan:"));
        statusPernikahanComboBox = new JComboBox<>(new String[]{"Menikah", "Belum Menikah"});
        inputPanel.add(statusPernikahanComboBox);

        // Alamat 1
        inputPanel.add(new JLabel("Alamat 1:"));
        alamat1Field = new JTextField();
        inputPanel.add(alamat1Field);

        // Alamat 2
        inputPanel.add(new JLabel("Alamat 2:"));
        alamat2Field = new JTextField();
        inputPanel.add(alamat2Field);

        // Submit Button
        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitForm();
            }
        });
        inputPanel.add(submitButton);

        // Load Button
        loadButton = new JButton("Load Data");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadDataFromFile();
            }
        });
        inputPanel.add(loadButton);

        // Panel Data
        JPanel dataPanel = new JPanel(new BorderLayout());
        listModel = new DefaultListModel<>();
        dataList = new JList<>(listModel);
        dataList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedIndex = dataList.getSelectedIndex();
                    if (selectedIndex != -1) {
                        showDetailDialog(selectedIndex);
                    }
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(dataList);
        dataPanel.add(scrollPane, BorderLayout.CENTER);

        // Add Panels to Frame
        add(inputPanel, BorderLayout.NORTH);
        add(dataPanel, BorderLayout.CENTER);

        // Inisialisasi list data karyawan
        dataKaryawanList = new ArrayList<>();
    }

    private void submitForm() {
        String nik = nikField.getText();
        String nama = namaField.getText();
        String alamat = alamatField.getText();
        String tempatLahir = tempatLahirField.getText();
        String tanggalLahir = tanggalLahirField.getText();
        String jenisKelamin = (String) jenisKelaminComboBox.getSelectedItem();
        String telepon = teleponField.getText();
        String statusPernikahan = (String) statusPernikahanComboBox.getSelectedItem();
        String alamat1 = alamat1Field.getText();
        String alamat2 = alamat2Field.getText();

        // Tampilkan data yang diinputkan
        JOptionPane.showMessageDialog(this, "Data Karyawan:\n" +
                "NIK: " + nik + "\n" +
                "Nama: " + nama + "\n" +
                "Alamat: " + alamat + "\n" +
                "Tempat Lahir: " + tempatLahir + "\n" +
                "Tanggal Lahir: " + tanggalLahir + "\n" +
                "Jenis Kelamin: " + jenisKelamin + "\n" +
                "Telepon: " + telepon + "\n" +
                "Status Pernikahan: " + statusPernikahan + "\n" +
                "Alamat 1: " + alamat1 + "\n" +
                "Alamat 2: " + alamat2);

        // Simpan data ke file
        saveDataToFile(nik, nama, alamat, tempatLahir, tanggalLahir, jenisKelamin, telepon, statusPernikahan, alamat1, alamat2);

        // Tambahkan data ke list model
        dataKaryawanList.add(new String[]{nik, nama, alamat, tempatLahir, tanggalLahir, jenisKelamin, telepon, statusPernikahan, alamat1, alamat2});
        listModel.addElement((dataKaryawanList.size()) + ". " + nama + " (" + nik + ")");
    }

    private void saveDataToFile(String nik, String nama, String alamat, String tempatLahir, String tanggalLahir, String jenisKelamin, String telepon, String statusPernikahan, String alamat1, String alamat2) {
        try (FileWriter writer = new FileWriter("karyawan.txt", true)) {
            writer.write("NIK: " + nik + "\n");
            writer.write("Nama: " + nama + "\n");
            writer.write("Alamat: " + alamat + "\n");
            writer.write("Tempat Lahir: " + tempatLahir + "\n");
            writer.write("Tanggal Lahir: " + tanggalLahir + "\n");
            writer.write("Jenis Kelamin: " + jenisKelamin + "\n");
            writer.write("Telepon: " + telepon + "\n");
            writer.write("Status Pernikahan: " + statusPernikahan + "\n");
            writer.write("Alamat 1: " + alamat1 + "\n");
            writer.write("Alamat 2: " + alamat2 + "\n");
            writer.write("-------------------------------\n");
            JOptionPane.showMessageDialog(this, "Data berhasil disimpan ke file karyawan.txt");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat menyimpan data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadDataFromFile() {
        dataKaryawanList.clear();
        listModel.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("karyawan.txt"))) {
            String line;
            StringBuilder dataBuilder = new StringBuilder();
            int count = 1;
            while ((line = reader.readLine()) != null) {
                dataBuilder.append(line).append("\n");
                if (line.startsWith("NIK: ")) {
                    String nik = line.substring(5).trim();
                    String nama = reader.readLine().substring(6).trim();
                    dataKaryawanList.add(new String[]{nik, nama, dataBuilder.toString()});
                    listModel.addElement(count + ". " + nama + " (" + nik + ")");
                    count++;
                    dataBuilder.setLength(0); // Bersihkan StringBuilder untuk entri berikutnya
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat memuat data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showDetailDialog(int index) {
        if (index >= 0 && index < dataKaryawanList.size()) {
            String[] data = dataKaryawanList.get(index);
            StringBuilder detailBuilder = new StringBuilder();
            detailBuilder.append("NIK: ").append(data[0]).append("\n");
            detailBuilder.append("Nama: ").append(data[1]).append("\n");
            detailBuilder.append("Alamat: ").append(data[2]).append("\n");
            detailBuilder.append("Tempat Lahir: ").append(data[3]).append("\n");
            detailBuilder.append("Tanggal Lahir: ").append(data[4]).append("\n");
            detailBuilder.append("Jenis Kelamin: ").append(data[5]).append("\n");
            detailBuilder.append("Telepon: ").append(data[6]).append("\n");
            detailBuilder.append("Status Pernikahan: ").append(data[7]).append("\n");
            detailBuilder.append("Alamat 1: ").append(data[8]).append("\n");
            detailBuilder.append("Alamat 2: ").append(data[9]).append("\n");

            JOptionPane.showMessageDialog(this, detailBuilder.toString(), "Detail Karyawan", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InputFormKaryawan().setVisible(true);
            }
        });
    }
}
