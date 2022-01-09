import PropTypes from 'prop-types'
import moment from 'moment'
import ImageActionButton from './ImageActionButton'
import HashtagList from '../hashtag/HashtagList'
import {photoUrl} from '../../redux/api/photoApi'
import './imageGallery.scss'

const ImageGallery = ({images, setSelectedImage}) => {
  return (
    <div className="row">
      {images.map((image) => (
        <div key={image.path} className="col-lg-4 col-md-6 p-2">
          <div className="row justify-content-between p-2">
            <div className="col-5"><b>{image.author}</b></div>
            <div className="col-5 text-end image-description-time">
              <p className="p-0 m-0">{moment(image.uploaded).format('YYYY-MM-DD')}</p>
              <p className="p-0 m-0">{moment(image.uploaded).format('hh:mm')}</p>
            </div>
          </div>
          <img onClick={() => setSelectedImage(image)} width="100%" alt={image.description}
               src={`${photoUrl}/download/${image.thumbnail}`} role="button"/>
          <div className="row justify-content-between p-2">
            <div className="col-9 overflow-hidden"><HashtagList hashtagItems={image.hashtags}/></div>
            <div className="col-3 text-end">
              <ImageActionButton type={image.ownershipType}/>
            </div>
          </div>
        </div>
      ))}
    </div>
  )
}

ImageGallery.propTypes = {
  setSelectedImage: PropTypes.func.isRequired,
  images: PropTypes.arrayOf(PropTypes.shape({
    path: PropTypes.string.isRequired,
    thumbnail: PropTypes.string.isRequired,
    author: PropTypes.string.isRequired,
    uploaded: PropTypes.string.isRequired,
    description: PropTypes.string,
    ownershipType: PropTypes.string,
    hashtags: PropTypes.array
  }))
}

ImageGallery.defaultProps = {
  images: []
}

export default ImageGallery