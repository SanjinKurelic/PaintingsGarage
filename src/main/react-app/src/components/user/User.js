import {Button, Card, Col, Container, Row} from 'react-bootstrap'
import {useFindImagesQuery} from '../../redux/api/photoApi'
import ImageGallery from '../image/ImageGallery'
import {useState} from 'react'
import {hostname} from '../../redux/api/baseApi'
import Image from '../image/Image'
import './user.scss'
import {useGetUserDetailsQuery} from '../../redux/api/userApi'
import {useAuth} from '../../hooks/useAuth'
import {useDispatch} from 'react-redux'
import {setCurrentUser} from '../../redux/slice/currentUserSlice'

const User = () => {
  const userDetails = useGetUserDetailsQuery()
  const dispatch = useDispatch()
  const {user} = useAuth()

  // Fetch images
  const userImages = useFindImagesQuery({authors: user.id})
  const [selectedImage, setSelectedImage] = useState(null)

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
          <Button className="user-action-button d-block m-2" variant="primary"
                  onClick={() => dispatch(setCurrentUser(null))}>Logout</Button>
        </Col>
      </Row>
        <div className="text-center">
          <Button className="user-action-button m-5 d-inline-block" variant="primary">Upload image</Button>
        </div>
      </>}
      {(userImages.isSuccess && userImages.data.length > 0) &&
        <ImageGallery images={userImages.data} setSelectedImage={setSelectedImage}/>}
      {selectedImage && <Image image={selectedImage} setSelectedImage={setSelectedImage}/>}
    </>
  )
}

export default User
