# Paintings Garage

Paintings garage is web shop where customers can upload/edit or buy physical or digital copy of painting. Customer can search for specifica author or hashtag. They can, also change their plan

## Getting started:

Paintings Garage is consisted of two main parts: backend and frontend. Backend is written in Java using Spring Boot, and frontend is written in JavaScript using React framewok. Images are stored in sftp which is provided with Docker image.

### Prerequisites

Running the project will require both Java and Node.js installed on system:

- Java/JDK 17+
- Node.js with NPM and Yarn
- Docker and docker compose

### Running

Run Docker compose command to start SFTP server which will handle all images:

```
docker-compose up
```

To start backend and build frontend, run Maven wrapper command which will build everyting and serve web application on **localhost:8080**

```
./mvnw spring-boot:run
```
 
#### Main page

After application is started main page is shown. Main page shows latest uploaded images.

<p align="center"><img src="https://github.com/SanjinKurelic/PaintingsGarage/blob/master/images/homeScreen.png"/></p>

If user click on one painting in gallery, image will be enralged and extra inforamations will be shown such as: title, author, upload date, description, hashtag and digiatal and physical painting price.

Depending on user type, user is offered with several actions below each image:

- Add to cart - if user is not logged-in or she/he is not the owner of an image
- Download - if user is the owner of a image
- Edit - if user is the author of a image
- Delete - if user is the author of a image

If user want's to delete an image, she or he will be prompted with confirmation dialog:

<p align="center"><img src="https://github.com/SanjinKurelic/PaintingsGarage/blob/master/images/deleteConfirmation.png"/></p>

If user owns an image, she or he can download full-size image without watermark (which is generated on backend for every non-owning image). Also, user can change image size and filter she or he whish to applay to image (if any):

<p align="center"><img src="https://github.com/SanjinKurelic/PaintingsGarage/blob/master/images/downloadImage.png"/></p>

On top of main screen there are several elements such as logo, search bar, user page (or login/register page) and cart page. In search bar user can search for images using several filters like: author name, hashtag, size (small, medium, big) or date of upload.

<p align="center"><img src="https://github.com/SanjinKurelic/PaintingsGarage/blob/master/images/searchBar.png"/></p>

If there are no results, user will be offered with option to show latest images.

### Cart page

User can add every non-owning image to cart. She or she can choose to buy digital copy of painting or physical painting. User can checkout her or his cart only if he is registered

<p align="center"><img src="https://github.com/SanjinKurelic/PaintingsGarage/blob/master/images/cartPage.png"/></p>

### Login page

<p align="center"><img src="https://github.com/SanjinKurelic/PaintingsGarage/blob/master/images/userLogin.png"/></p>

Every user or administrator can login to application using email defined in `data.sql` file. Password for each user is:

> name + "123"
 
Example, for user picasso:

> picasso123

### User page

<p align="center"><img src="https://github.com/SanjinKurelic/PaintingsGarage/blob/master/images/userPage.png"/></p>

<p align="center"><img src="https://github.com/SanjinKurelic/PaintingsGarage/blob/master/images/uploadNewImage.png"/></p>

### Admin page

<p align="center"><img src="https://github.com/SanjinKurelic/PaintingsGarage/blob/master/images/adminPanel.png"/></p>


<p align="center"><img src="https://github.com/SanjinKurelic/PaintingsGarage/blob/master/images/changeUserPlan.png"/></p>

## Dependencies

## Licence

See the LICENSE file. For every question write to kurelic@sanjin.eu
