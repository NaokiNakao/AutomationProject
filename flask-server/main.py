from netmiko import ConnectHandler
from getpass import getpass

cisco1 = { 
    "device_type": "cisco_ios_telnet",
    "host": "192.168.100.235",
    "port": 5000
}

# Show command that we execute.
command = "show ip int brief"

with ConnectHandler(**cisco1) as net_connect:
    output = net_connect.send_command(command)

# Automatically cleans-up the output so that only the show output is returned
print()
print(output)
print()