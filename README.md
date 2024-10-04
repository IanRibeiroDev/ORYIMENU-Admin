# ORIY MENU ADMIN - Aplicação do Administrador

Esta é uma aplicação voltada para administradores de restaurantes, permitindo o gerenciamento dos pratos e cardápios. Através dela, o administrador pode cadastrar, editar e remover pratos, além de organizar cardápios de forma fácil e eficiente.

## Funcionalidades

- **Gerenciamento de Pratos**:
    - Adição, edição e remoção de pratos com foto, descrição e tipo.
- **Montagem de Cardápios**:
    - Criação e gerenciamento de cardápios diários, associando pratos a diferentes dias da semana.

## Tecnologias Utilizadas

- **Jetpack Compose**: Interface de usuário moderna e reativa.
- **Firebase**: Backend serverless para gerenciamento de dados e imagens.
    - **Firebase Authentication**: Autenticação segura para administradores.
    - **Firebase Realtime Database**: Armazenamento e gerenciamento dos pratos e cardápios.
    - **Firebase Storage**: Armazenamento das imagens dos pratos.
- **MVVM (Model-View-ViewModel)**: Arquitetura para garantir a separação de responsabilidades e facilitar a manutenção.

## Repositório integrado

Esta aplicação será visualizada pelo administrador. Para visualizar os pratos sem ter o poder de editá-los (um cardápio), há uma outra aplicação cujo propósito é funcionar com um cardápio. O link dela é https://github.com/IanRibeiroDev/ORYIMENU-Admin.

## Como Rodar o Projeto

1. Clone o repositório
```bash
git clone https://github.com/yagocdj/oriy-menu.git
```

2. Abra o projeto no Android Studio.

3. Configure o Firebase seguindo as instruções na documentação oficial.

4. Conecte um dispositivo ou emulador e execute o aplicativo.
