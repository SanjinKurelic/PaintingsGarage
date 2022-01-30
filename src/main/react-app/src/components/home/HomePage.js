import {Button} from 'react-bootstrap'
import ImageGallery from '../image/ImageGallery'
import Image from '../image/Image'
import {useGetPhotoListQuery} from '../../redux/api/baseApi'
import {useState} from 'react'
import PropTypes from 'prop-types'

const HomePage = ({searchImageResults, searchFired, setSearchFired, setClearSearch}) => {
  const latestImages = useGetPhotoListQuery()
  const [selectedImage, setSelectedImage] = useState(null)

  const images = () => {
    if (searchFired && searchImageResults.isSuccess) {
      return searchImageResults.data
    }
    if (!searchFired && latestImages.isSuccess) {
      return latestImages.data
    }
    return []
  }

  return (
    <>
      {(images().length === 0 && searchFired) && <div className="m-3 fst-italic text-danger">
        Sorry, but nothing matched your search terms. Please try again with some different keywords or&nbsp;
        <Button variant="link" className="fst-italic p-0 align-baseline" onClick={() => {
          setSearchFired(false)
          setClearSearch(true)
        }}>view latest images.</Button>
      </div>}
      <ImageGallery images={images()} setSelectedImage={setSelectedImage}/>
      {selectedImage && <Image image={selectedImage} closeCallback={() => setSelectedImage(null)}/>}
    </>
  )
}

HomePage.propTypes = {
  searchImageResults: PropTypes.shape({
    isSuccess: PropTypes.bool,
    data: PropTypes.array
  }),
  searchFired: PropTypes.bool.isRequired,
  setSearchFired: PropTypes.func.isRequired,
  setClearSearch: PropTypes.func.isRequired
}

export default HomePage
