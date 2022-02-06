import ImageGallery from '../image/ImageGallery'
import {useState} from 'react'
import Image from '../image/Image'
import UserDetails from './UserDetails'
import {Button} from 'react-bootstrap'
import {useFindImageQuery, useGetUserQuery} from '../../redux/api/baseApi'
import {useDispatch, useSelector} from 'react-redux'
import {selectCurrentUser} from '../../redux/slice/currentUserSlice'
import {showDialog} from '../../redux/slice/currentDialogSlice'
import {DialogType} from '../dialog/AllDialogs'

const User = () => {
  const currentUser = useSelector(selectCurrentUser)
  const userDetails = useGetUserQuery()

  // Fetch images
  const userImages = useFindImageQuery({authors: currentUser.id})
  const [selectedImage, setSelectedImage] = useState(null)

  // Upload image
  const dispatch = useDispatch()
  const uploadImage = () => {
    dispatch(showDialog({type: DialogType.UPLOAD, data: () => userImages.refetch()}))
  }

  return (
    <>
      {userDetails.isSuccess && <UserDetails className="m-4 text-center" userDetails={userDetails.data}/>}
      {userDetails.isSuccess && userDetails.data.plan === 'ARTIST' && <div className="text-center">
        <Button className="button-dark m-5 d-inline-block" variant="primary" onClick={uploadImage}>Upload image</Button>
      </div>}
      {(userImages.isSuccess && userImages.data.length > 0) &&
        <ImageGallery images={userImages.data} setSelectedImage={setSelectedImage}/>}
      {selectedImage &&
        <Image image={selectedImage} setSelectedImage={setSelectedImage} closeCallback={() => setSelectedImage(null)}/>}
    </>
  )
}

export default User
