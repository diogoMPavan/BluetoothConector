BluetoothConector 📱💻

Aplicativo Android para envio de mensagens via Bluetooth para dispositivos pareados, e um script Python que recebe essas mensagens no computador.

📱 Sobre o Aplicativo Android

O BluetoothConector é um aplicativo simples que:
- Escaneia dispositivos Bluetooth próximos.
- Permite parear com dispositivos encontrados.
- Envia mensagens via conexão Bluetooth para um dispositivo escolhido.
- Funciona de forma prática para testar comunicações entre o celular e qualquer dispositivo Bluetooth, como outro celular, módulos de eletrônica (ex: Arduino), ou um computador.

🖥️ Sobre o Script Python (Receptor)

O script Python foi feito para rodar no computador e ler mensagens enviadas pelo app Android.

- Abre a porta serial Bluetooth criada ao parear o celular com o notebook.
- Escuta constantemente mensagens que chegam.
- Imprime no terminal cada mensagem recebida.

Ideal para testes de comunicação simples, como simular um chat ou controlar sistemas via Bluetooth.

🚀 Como Usar
1. No Celular (Android)
- Instale o app no seu dispositivo Android.
- Ative o Bluetooth do celular.
- Abra o app.
- No topo:
  - Um spinner lista dispositivos disponíveis (atualizando automaticamente).
  - Um botão para parear com o dispositivo selecionado.
- No meio:
  - Um spinner com dispositivos pareados.
- Embaixo:
  - Digite a mensagem.
  - Clique em "Enviar" para mandar ao dispositivo escolhido.

2. No Computador (Notebook)
- Pareie o notebook com o seu celular via Bluetooth (normalmente pelo Windows Settings ou Painel de Controle).
- Identifique a porta COM associada ao Bluetooth.
- Windows: "Gerenciador de Dispositivos" → "Portas (COM e LPT)".

Edite o script receptor.py (nome sugerido) e coloque o nome correto da porta COM:
python receptor.py
Envie mensagens pelo app Android — elas aparecerão no terminal do notebook.

🛠️ Tecnologias Usadas
- Android App:
  - Java
  - Android Studio
- Bluetooth API:
  - android.bluetooth.*
- Python Script:
  - Python 3
  - pyserial

⚡ Observações Importantes
Permissões no Android:
- Necessário permitir Bluetooth, Localização e Conexão no app.
- Versões Android:
- Em Android 12+ (API 31+), as permissões Bluetooth são mais restritas.

Taxa de transmissão:
- O script e o app utilizam padrão de 9600 bps para comunicação.
------------------------------------------------------------------------------
BluetoothConector 📱💻

Android app for sending messages via Bluetooth to paired devices, and a Python script to receive these messages on a computer.

📱 About the Android App
BluetoothConector is a simple app that:
- Scans for nearby Bluetooth devices.
- Allows pairing with discovered devices.
- Sends messages via Bluetooth connection to a selected device.
- Provides a practical way to test communication between your phone and any Bluetooth device, such as another phone, electronics modules (e.g., Arduino), or a computer.

🖥️ About the Python Script (Receiver)

The Python script is designed to run on a computer and receive messages sent from the Android app.
- Opens the Bluetooth serial port created after pairing the phone with the computer.
- Constantly listens for incoming messages.
- Prints each received message to the terminal.
- Ideal for simple communication tests, like simulating a chat or controlling systems via Bluetooth.

🚀 How to Use
1. On Your Phone (Android)
  - Install the app on your Android device.
  - Turn on Bluetooth.
  - Open the app.
- At the top:
  - A spinner lists available devices (refreshes automatically).
  -A button allows you to pair with the selected device.
- In the middle:
  - A spinner displays paired devices.
- At the bottom:
  - Type your message.
  - Click "Send" to send it to the selected device.
  - 
2. On Your Computer (Laptop)
- Pair your laptop with your phone via Bluetooth (usually through Windows Settings or Control Panel).
- Find the COM port associated with the Bluetooth connection.
- On Windows: "Device Manager" → "Ports (COM & LPT)".
- Edit the receptor.py script (suggested name) and set the correct COM port name:
- python receptor.py
- Send messages through the Android app — they will appear in the laptop's terminal.

🛠️ Technologies Used

- Android App:
  - Java
  - Android Studio
- Bluetooth API:
  - android.bluetooth.*
- Python Script:
- Python 3
- pyserial

⚡ Important Notes
- Android Permissions:
  - Bluetooth, Location, and Connection permissions must be granted.
- Android Versions:
  - On Android 12+ (API 31+), Bluetooth permissions have become more restrictive.

- Transmission Rate:
- Both the app and the script use the standard 9600 bps baud rate for communication.
