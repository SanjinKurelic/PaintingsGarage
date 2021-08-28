import PropTypes from 'prop-types'
import moment from 'moment'
import ImageActionButton from './ImageActionButton'
import HashtagList from '../hashtag/HashtagList'

const ImageGallery = ({images}) => {
  return (
    <div className='row'>
      {images.map((image) => (
        <div key={image.path} className='col-lg-4 col-md-6 p-2'>
            <div className='row justify-content-between p-2'>
              <div className='col-5'><b>{image.author}</b></div>
              <div className='col-5 text-end'>
                <p className='p-0 m-0' style={{fontSize: 'xx-small'}}>{moment(image.uploaded).format('YYYY-MM-DD')}</p>
                <p className='p-0 m-0' style={{fontSize: 'xx-small'}}>{moment(image.uploaded).format('hh:mm')}</p>
              </div>
            </div>
            <img width='100%' alt={image.description} src={'http://localhost:8080/api/file/download/' + image.thumbnail + '.jpg'}/>
            <div className='row justify-content-between p-2'>
              <div className='col-9 overflow-hidden'><HashtagList hashtagItems={image.hashTags} /></div>
              <div className='col-3 text-end'>
                <ImageActionButton type={image.ownershipType} />
              </div>
            </div>
        </div>
      ))}
    </div>
  )
}

ImageGallery.propTypes = {
  images: PropTypes.arrayOf(PropTypes.shape({
    path: PropTypes.string.isRequired,
    thumbnail: PropTypes.string.isRequired,
    author: PropTypes.string.isRequired,
    uploaded: PropTypes.instanceOf(Date).isRequired,
    description: PropTypes.string,
    ownershipType: PropTypes.string,
    hashTags: PropTypes.array
  }))
}

ImageGallery.defaultProps = {
  images: []
}

export default ImageGallery