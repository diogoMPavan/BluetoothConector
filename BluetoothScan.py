import serial

# Porta em: Gerenciador de Dispositivos >> Portas >> Serial Padrão por Link Bluetooth
PORT = 'COM7'
BAUDRATE = 9600  

try:
    # Abre a porta serial
    ser = serial.Serial(PORT, BAUDRATE, timeout=1)
    print(f"Conectado na porta {PORT}!")

    while True:
        # Lê uma linha de dados
        if ser.in_waiting > 0:
            data = ser.readline().decode('utf-8').strip()
            print(f"Recebido: {data}")

except serial.SerialException as e:
    print(f"Erro de conexão: {e}")

except KeyboardInterrupt:
    print("\nEncerrando conexão...")
    ser.close()
