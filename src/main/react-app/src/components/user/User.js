import {useFindImagesQuery} from '../../redux/api/photoApi'
import ImageGallery from '../image/ImageGallery'
import {useState} from 'react'
import Image from '../image/Image'
import './user.scss'
import {useGetUserDetailsQuery} from '../../redux/api/userApi'
import {useAuth} from '../../hooks/useAuth'
import UserDetails from './UserDetails'
import {Button} from 'react-bootstrap'

const User = () => {
  const userDetails = useGetUserDetailsQuery()
  const {user} = useAuth()

  // Fetch images
  const userImages = useFindImagesQuery({authors: user.id})
  const [selectedImage, setSelectedImage] = useState(null)

  return (
    <>
      {userDetails.isSuccess && <UserDetails className="mt-4" userDetails={userDetails.data}/>}
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
