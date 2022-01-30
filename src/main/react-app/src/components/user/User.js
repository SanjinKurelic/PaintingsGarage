import ImageGallery from '../image/ImageGallery'
import {useState} from 'react'
import Image from '../image/Image'
import './user.scss'
import UserDetails from './UserDetails'
import {Button} from 'react-bootstrap'
import {useFindImageQuery, useGetUserQuery} from '../../redux/api/baseApi'

const User = () => {
  const userDetails = useGetUserQuery()

  // Fetch images
  const userImages = useFindImageQuery({authors: userDetails.id})
  const [selectedImage, setSelectedImage] = useState(null)

  return (
    <>
      {userDetails.isSuccess && <UserDetails className="m-4 text-center" userDetails={userDetails.data}/>}
      {userDetails.isSuccess && userDetails.data.plan === 'ARTIST' && <div className="text-center">
        <Button className="user-action-button m-5 d-inline-block" variant="primary">Upload image</Button>
      </div>}
      {(userImages.isSuccess && userImages.data.length > 0) &&
        <ImageGallery images={userImages.data} setSelectedImage={setSelectedImage}/>}
      {selectedImage && <Image image={selectedImage} setSelectedImage={setSelectedImage}/>}
    </>
  )
}

export default User
