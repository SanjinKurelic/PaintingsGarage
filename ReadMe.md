# Paintings Garage

Paintings garage is a web shop where customers can sell and buy physical or digital copies of paintings. Customer can upload, edit or remove their paintings and also view others paintings or search for specific author or hashtag. Customer can choose between two user plans: buyer - can only buy paintings, or artist - can sell and buy paintings. There is also a page for administrator, who can see user activity and change user plans or edit/remove uploaded images. Authentication is done using OAuth, and registration for new users can be done through an email or Google and GitHub accounts.

## Getting started:

Paintings Garage is consisted of two main parts: backend and frontend. Backend is written in Java using Spring Boot, and frontend is written in JavaScript using React framework. Images of paintings are stored in SFTP which is provided with Docker image.

### Prerequisites

Running the project will require both Java and Node.js installed on the system:

- Java/JDK 17+
- Node.js with NPM and Yarn
- Docker and docker compose

### Running

Run Docker compose command to start SFTP server which will store all uploaded images:

```
docker-compose up
```

Some images are already provided with the project and will be automatically copied to SFTP server.

To start backend and build frontend, run Maven wrapper command which will build everything and serve web application
on **localhost:8080**

```
./mvnw spring-boot:run
```

Maven wrapper will build Java Spring Boot project and will also run yarn commands to build frontend and copy it to
backend resource directory.

> Note: If you would like to run this web application in Dockerfile container, please follow usage notes mentioned in
> Dockerfile

#### Main page

After application is started main page is shown. Main page shows latest uploaded images.

<p align="center"><img src="https://github.com/SanjinKurelic/PaintingsGarage/blob/master/images/homeScreen.png"/></p>

After user click on one painting in gallery, image will be enlarged and extra information will be shown such as: title,
author, upload date, description, hashtag and digital and physical painting price. If the user is not the owner or
author of the image, watermark text will be generated on the backend, which will cover a great amount of the image. To
additionally secure copying the image, right click is disabled on image view dialog. Of course, more skilled users will
be able to download **watermarked** images in full size.

Depending on the user type, the user is offered with several actions below each image:

- Add to cart - if user is not logged-in, or he is not the owner of an image
- Download - if user is the owner of an image
- Edit - if user is the author of an image
- Delete - if user is the author of an image

If the user doesn't own the image, but he wants to buy it, he can click on "Add to cart" button. A dialog will pop up and ask the user what kind of painting he wants to buy: digital copy (less expensive) or real physical painting (more expensive).

If a user owns an image, she or he can download the full-size image without watermark. Before download occur, user can change image size and filter to apply to the image (if any). Changing image dimensions/size and applying filters are done on backend.

<p align="center"><img src="https://github.com/SanjinKurelic/PaintingsGarage/blob/master/images/downloadImage.png"/></p>

If the user owns an image he can also edit information like: title, description and list of hashtags.

If the user wants to delete an image, she or he will be prompted with a confirmation dialog. Images are automatically deleted from database and from SFTP server.

<p align="center"><img src="https://github.com/SanjinKurelic/PaintingsGarage/blob/master/images/deleteConfirmation.png"/></p>

##### Header

Header, on top of a page, contains several elements such as logo, search bar, user page (or login/register page) and cart page. In search bar user can search for images using various filters like: author name, hashtag, size (small, medium, big) or date of upload. User can also clear search filters and return to the latest images if there are no images which meet the required query.

<p align="center"><img src="https://github.com/SanjinKurelic/PaintingsGarage/blob/master/images/searchBar.png"/></p>

### Cart page

User can add every non-owning image to the cart. He can choose to buy a digital copy of the painting or a physical painting. User can edit cart items (remove some or all) and also he can check out. If user check out, and he is not authenticated he will be redirected to login/register page, otherwise he will become the owner of an image and cart will be emptied. (Payment is not implemented)

<p align="center"><img src="https://github.com/SanjinKurelic/PaintingsGarage/blob/master/images/cartPage.png"/></p>

### Login page

Every user can register to web shop using an email, Google or GitHub account. If the user already has an account, he will be able to log in. There are several demo accounts defined in `data.sql` file in resource directory of Maven backend part of project. The password for each user (including administrator) is in format:

> name + "123"
 
Example, for user Picasso, login credentials are:

> username: picasso@example.com
> 
> password: picasso123

For administrator, credentials are:

> username:  admin@example.com
> 
> password: admin123

<p align="center"><img src="https://github.com/SanjinKurelic/PaintingsGarage/blob/master/images/userLogin.png"/></p>

Currently, there is only frontend validation implement for login/register values.

### User page

Only authenticated users that have an USER role can see the user page. On this page user can see his/her details like name, email and date of registration. They can also log out, change their user plan and add, edit or remove all of their images. Of course, if they are not the author of an image, they can only download it.

<p align="center"><img src="https://github.com/SanjinKurelic/PaintingsGarage/blob/master/images/userPage.png"/></p>

While adding a new image, user can define title, description, digital and physical price and hashtags. Images must be in one of supported formats: JPG/JPEG, PNG and BMP. All formats are defined in `application.yaml` file in Maven resource directory and can be easily extend to support even more file formats.

<p align="center"><img src="https://github.com/SanjinKurelic/PaintingsGarage/blob/master/images/uploadNewImage.png"/></p>

### Admin page

Only authenticated users that have an ADMIN role can see the admin page. Admin can view the latest user activities, which are grouped in three categories:

- authorization activity (login and registration of the new user)
- user activity (changing the user plan)
- image activity (buying, uploading, editing and removing an image)

<p align="center"><img src="https://github.com/SanjinKurelic/PaintingsGarage/blob/master/images/adminPanel.png"/></p>

The administrator can do everything that a normal registered user can do, which means the administrator can edit every uploaded  image to the website and also change the user plan for each registered user.

<p align="center"><img src="https://github.com/SanjinKurelic/PaintingsGarage/blob/master/images/changeUserPlan.png"/></p>

## Test cases

This project is covered with unit, integration and UI test cases. Integration and unit test cases are done using JUnit/Jupiter, mockito-inline and Testcontainers. UI integration test with backend is done using Selenium test suite.

> Note: Before running Selenium test cases please make sure that you have running application on port 8080:
>
> ```./mvnw spring-boot:run```
> 
> This requirement can't be easily overcome using something like:
> 
> ```@SpringBootTest(classes = PaintingsGarageApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)```
> 
> because we are building React frontend in Maven build process. Fix would require using Testcontainers that read Dockerfile which loads maven and all project sources, build a _jar_ file and then run it in container by exposing defined port.

## Monitoring and health check

Health check is implemented using Spring Actuator with one custom health check for SFTP server availability. Health is exposed on actuator/health endpoint:

```
http://localhost:8080/actuator/health
```

Also, project is packed with Grafana and Prometheus monitoring tools. Both technologies are run from docker-compose and
already set-up for the project. There is also one custom metric defined in Grafana which measure image filters speed:

<p align="center"><img src="https://github.com/SanjinKurelic/PaintingsGarage/blob/master/images/grafana.png"/></p>

Prometheus can be accessed from: `http://localhost:19090`

Grafana can be accessed from: `http://localhost:13000` with username `admin` and password `admin`.

## Dependencies and technologies

Backend:

- Java 17
- Spring Boot
- Spring Security
- Spring Aspect (AOP)
- Spring Actuator
- MapStruct
- Lombok
- Hibernate
- JPA
- SSHJ
- JWT
- OAuth
- Micrometer
- Prometheus
- Grafana

Frontend:

- JavaScript
- NodeJS/NPM
- Yarn
- React
- Redux
- Redux persist
- Redux toolkit (RTK)
- React router
- Bootstrap
- SASS
- React icons
- React datepicker
- React cart
- React tag input
- PropTypes
- Moment
- File saver

Test cases:

- JUnit
- Jupiter
- Selenium
- Selenium Fluent
- Mockito-inline
- Testcontainers

## Licence

See the LICENSE file. For every question write to kurelic@sanjin.eu
