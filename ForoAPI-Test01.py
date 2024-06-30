import requests
import logging

# Configuración del log
logging.basicConfig(filename='foroapi-test.log', level=logging.INFO, 
                    format='%(asctime)s:%(levelname)s:%(message)s')

# URL base de la API
base_url = 'http://localhost:8080'

# Función para probar listar tópicos
def test_listar_topicos():
    url = f'{base_url}/topicos'
    response = requests.get(url)
    logging.info(f'GET {url} - Status Code: {response.status_code}, Response: {response.text}')

# Función para probar registrar un nuevo tópico
def test_registrar_topico():
    url = f'{base_url}/topicos'
    payload = {
        "idUsuario": 1,
        "mensaje": "Este es un nuevo tópico",
        "nombreCurso": "Java",
        "titulo": "Nuevo Tópico"
    }
    response = requests.post(url, json=payload)
    logging.info(f'POST {url} - Status Code: {response.status_code}, Response: {response.text}')

# Función para probar visualizar un tópico específico
def test_visualizar_topico(id):
    url = f'{base_url}/topicos/{id}'
    response = requests.get(url)
    logging.info(f'GET {url} - Status Code: {response.status_code}, Response: {response.text}')

# Función para probar actualizar un tópico
def test_actualizar_topico(id):
    url = f'{base_url}/topicos/{id}'
    payload = {
        "mensaje": "Mensaje actualizado",
        "nombreCurso": "Spring",
        "titulo": "Tópico Actualizado"
    }
    response = requests.put(url, json=payload)
    logging.info(f'PUT {url} - Status Code: {response.status_code}, Response: {response.text}')

# Función para probar eliminar un tópico
def test_eliminar_topico(id):
    url = f'{base_url}/topicos/{id}'
    response = requests.delete(url)
    logging.info(f'DELETE {url} - Status Code: {response.status_code}, Response: {response.text}')

# Función para probar listar usuarios
def test_listar_usuarios():
    url = f'{base_url}/usuarios'
    response = requests.get(url)
    logging.info(f'GET {url} - Status Code: {response.status_code}, Response: {response.text}')

# Función para probar registrar un nuevo usuario
def test_registrar_usuario():
    url = f'{base_url}/usuarios'
    payload = {
        "nombre": "Juan",
        "email": "juan@example.com",
        "password": "password123"
    }
    response = requests.post(url, json=payload)
    logging.info(f'POST {url} - Status Code: {response.status_code}, Response: {response.text}')

# Función para probar visualizar un usuario específico
def test_visualizar_usuario(id):
    url = f'{base_url}/usuarios/{id}'
    response = requests.get(url)
    logging.info(f'GET {url} - Status Code: {response.status_code}, Response: {response.text}')

# Función para probar actualizar un usuario
def test_actualizar_usuario(id):
    url = f'{base_url}/usuarios/{id}'
    payload = {
        "nombre": "Juan Actualizado",
        "email": "juan_actualizado@example.com",
        "password": "password456"
    }
    response = requests.put(url, json=payload)
    logging.info(f'PUT {url} - Status Code: {response.status_code}, Response: {response.text}')

# Función para probar eliminar un usuario
def test_eliminar_usuario(id):
    url = f'{base_url}/usuarios/{id}'
    response = requests.delete(url)
    logging.info(f'DELETE {url} - Status Code: {response.status_code}, Response: {response.text}')

# Ejecutar las pruebas
test_listar_topicos()
test_registrar_topico()
test_visualizar_topico(1)
test_actualizar_topico(1)
test_eliminar_topico(1)
test_listar_usuarios()
test_registrar_usuario()
test_visualizar_usuario(1)
test_actualizar_usuario(1)
test_eliminar_usuario(1)
