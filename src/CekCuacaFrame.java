import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.table.DefaultTableModel;
import org.json.JSONObject;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author HP
 */
public class CekCuacaFrame extends javax.swing.JFrame {

    /**
     * Creates new form CekCuacaFrame
     */
    public CekCuacaFrame() {
        initComponents();
        // Action Listener untuk cek cuaca
        checkWeatherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String city = cityTextField.getText();
                if (!city.isEmpty()) {
                    getWeather(city);
                } else {
                    JOptionPane.showMessageDialog(null, "Masukkan nama kota!");
                }
            }
        });
        cityComboBox.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedCity = (String) cityComboBox.getSelectedItem();
            if (selectedCity != null && !selectedCity.isEmpty()) {
                getWeather(selectedCity); // Memanggil fungsi pengecekan cuaca
            }
        }
    });
    }
    private String translateWeatherCondition(String weather) {
        switch (weather.toLowerCase()) {
            case "clear":
                return "Cerah";
            case "clouds":
                return "Berawan";
            case "rain":
                return "Hujan";
            case "snow":
                return "Salju";
            case "thunderstorm":
                return "Badai";
            case "mist":
                return "Kabut";
            case "drizzle":
                return "Gerimis";
            default:
                return "Kondisi tidak diketahui";
        }
    }
    private void getWeather(String city) {
    try {
        String apiKey = "bf57483a174753dcf473af8811a4bbab"; // Ganti dengan API Key Anda
        String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        conn.disconnect();

        JSONObject json = new JSONObject(content.toString());
        String weather = json.getJSONArray("weather").getJSONObject(0).getString("main");

        // Terjemahkan kondisi cuaca ke bahasa Indonesia
        String translatedWeather = translateWeatherCondition(weather);
        
        // Tampilkan kondisi cuaca yang diterjemahkan
        weatherLabel.setText("Kondisi Cuaca: " + translatedWeather);

        // Tampilkan gambar cuaca
        weatherImageLabel.setIcon(getWeatherImage(weather));
        
        // Tambahkan data cuaca ke JTable
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.addRow(new Object[]{city, translatedWeather});

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
    }
}

    
    private ImageIcon getWeatherImage(String weather) {
        String imagePath;
            switch (weather.toLowerCase()) {
                case "clear":
                    imagePath = "/images/clear.png";
                    break;
                case "clouds":
                    imagePath = "/images/cloudy.png";
                    break;
                case "rain":
                    imagePath = "/images/rain.png";
                    break;
                case "snow":
                    imagePath = "/images/snow.png";
                    break;
                case "thunderstorm":
                    imagePath = "/images/thunderstorm.png";
                    break;
                case "mist":
                    imagePath = "/images/mist.png";
                    break;
                case "drizzle":
                    imagePath = "/images/drizzle.png";
                    break;
                default:
                    imagePath = "/images/default.png"; // Gambar default jika kondisi tidak dikenali
                    break;
            }
            return new ImageIcon(getClass().getResource(imagePath));
    }


    private boolean isCityInComboBox(String city) {
        for (int i = 0; i < cityComboBox.getItemCount(); i++) {
            if (cityComboBox.getItemAt(i).equalsIgnoreCase(city)) {
                return true;
            }
        }
        return false;
    }
    
    private void saveTableDataToCSV() {
        try {
            FileWriter csvWriter = new FileWriter("dataCuaca.csv");

            // Header CSV
            csvWriter.append("Kota, Kondisi Cuaca\n");

            // Data dari JTable
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                csvWriter.append(jTable1.getValueAt(i, 0).toString());
                csvWriter.append(",");
                csvWriter.append(jTable1.getValueAt(i, 1).toString());
                csvWriter.append("\n");
            }
            csvWriter.flush();
            csvWriter.close();
            JOptionPane.showMessageDialog(this, "Data berhasil disimpan ke CSV");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void loadTableDataFromCSV() {
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader("dataCuaca.csv"));
            String row;
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0); // Hapus data lama di JTable

            csvReader.readLine(); // Lewati header
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                model.addRow(data);
            }
            csvReader.close();
            JOptionPane.showMessageDialog(this, "Data berhasil dimuat dari CSV");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        weatherLabel = new javax.swing.JLabel();
        cityTextField = new javax.swing.JTextField();
        checkWeatherButton = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        cityComboBox = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        saveToFileButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        addFavoriteButton = new javax.swing.JButton();
        loadFromFileButton = new javax.swing.JButton();
        weatherImageLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Nama Kota");

        weatherLabel.setText("Kondisi Cuaca:");

        cityTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cityTextFieldActionPerformed(evt);
            }
        });

        checkWeatherButton.setText("Cek");
        checkWeatherButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkWeatherButtonActionPerformed(evt);
            }
        });

        jButton2.setText("Keluar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        cityComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Banjarmasin", "Jakarta", "Surabaya", "Yogyakarta", "Bandung" }));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kota", "Kondisi Cuaca"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        saveToFileButton.setText("Simpan");
        saveToFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveToFileButtonActionPerformed(evt);
            }
        });

        jLabel2.setText("Nama Kota Favorit");

        addFavoriteButton.setText("Tambah Favorit");
        addFavoriteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addFavoriteButtonActionPerformed(evt);
            }
        });

        loadFromFileButton.setText("Memuat");
        loadFromFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadFromFileButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 95, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(weatherImageLabel)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(cityComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(weatherLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(loadFromFileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(checkWeatherButton, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(saveToFileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(addFavoriteButton)))))
                .addContainerGap(95, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(checkWeatherButton)
                        .addComponent(saveToFileButton)
                        .addComponent(addFavoriteButton)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton2)
                        .addComponent(loadFromFileButton))
                    .addComponent(weatherLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cityComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(weatherImageLabel)
                .addContainerGap(229, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cityTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cityTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cityTextFieldActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void checkWeatherButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkWeatherButtonActionPerformed
        String city = cityTextField.getText();
        if (!city.isEmpty()) {
            getWeather(city); // Memanggil fungsi pengecekan cuaca
        } else {
            JOptionPane.showMessageDialog(null, "Masukkan nama kota!");
        }
    }//GEN-LAST:event_checkWeatherButtonActionPerformed

    private void addFavoriteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFavoriteButtonActionPerformed
        String city = cityTextField.getText();
            if (!city.isEmpty() && !isCityInComboBox(city)) {
                cityComboBox.addItem(city);
            }
    }//GEN-LAST:event_addFavoriteButtonActionPerformed

    private void saveToFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveToFileButtonActionPerformed
        saveTableDataToCSV();
    }//GEN-LAST:event_saveToFileButtonActionPerformed

    private void loadFromFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadFromFileButtonActionPerformed
        loadTableDataFromCSV();
    }//GEN-LAST:event_loadFromFileButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CekCuacaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CekCuacaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CekCuacaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CekCuacaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CekCuacaFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addFavoriteButton;
    private javax.swing.JButton checkWeatherButton;
    private javax.swing.JComboBox<String> cityComboBox;
    private javax.swing.JTextField cityTextField;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton loadFromFileButton;
    private javax.swing.JButton saveToFileButton;
    private javax.swing.JLabel weatherImageLabel;
    private javax.swing.JLabel weatherLabel;
    // End of variables declaration//GEN-END:variables
}
