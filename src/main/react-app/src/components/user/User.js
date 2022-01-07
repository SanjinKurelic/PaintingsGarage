import {useHistory} from 'react-router-dom'
import {Button, Card, Col, Container, Row} from 'react-bootstrap'
import {useLazyFindImagesQuery} from '../../redux/api/photoApi'
import ImageGallery from '../image/ImageGallery'
import {useEffect, useState} from 'react'
import {hostname} from '../../redux/api/baseApi'
import Image from '../image/Image'
import './user.scss'
import {logoutCurrentUser} from '../../redux/auth'
import {useGetUserDetailsQuery} from '../../redux/api/userApi'

const User = () => {
  const userDetails = useGetUserDetailsQuery()

  // Redirect if user is not logged in
  const history = useHistory()
  useEffect(() => {
    if (userDetails.isError) {
      history.push('/login')
    }
  }, [history, userDetails])

  // Fetch images
  const [findUserImages, userImages] = useLazyFindImagesQuery()
  const [selectedImage, setSelectedImage] = useState(null)

  useEffect(() => {
    if (userDetails.isSuccess) {
      findUserImages({authors: userDetails.data.id})
    }
  }, [userDetails, findUserImages])

  return (
    <>
      {userDetails.isSuccess && <><Row className="mt-4">
        <Col className="text-center">
          <img alt="" src={hostname + '/' + userDetails.data.avatar} height="128"/>
        </Col>
        <Col xs={8}>
          <Card className="user-details">
            <Card.Body>
              <Card.Title>User details</Card.Title>
              <Card.Text>
                <Container>
                  <Row>
                    <Col className="fw-bold">Name:</Col>
                    <Col>{userDetails.data.name}</Col>
                  </Row>
                  <Row>
                    <Col className="fw-bold">Email:</Col>
                    <Col>{userDetails.data.email}</Col>
                  </Row>
                  <Row>
                    <Col className="fw-bold">Date of registration:</Col>
                    <Col>{userDetails.data.registered}</Col>
                  </Row>
                </Container>
              </Card.Text>
            </Card.Body>
          </Card>
        </Col>
        <Col>
          <Button className="user-action-button m-2" variant="primary"
                  onClick={() => logoutCurrentUser()}>Logout</Button>
        </Col>
      </Row>
        <div className="text-center">
          <Button className="user-action-button m-5 d-inline-block" variant="primary"
                  onClick={() => logoutCurrentUser()}>Upload
            image</Button>
        </div>
      </>}
      {(userImages.isSuccess && userImages.data.length > 0) &&
        <ImageGallery images={userImages.data} setSelectedImage={setSelectedImage}/>}
      {selectedImage && <Image image={selectedImage} setSelectedImage={setSelectedImage}/>}
    </>
  )
}

export default User
