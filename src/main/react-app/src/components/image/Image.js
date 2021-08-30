import PropTypes from 'prop-types'
import moment from 'moment'
import ImageActionButton from './ImageActionButton'
import {AiOutlineCloseCircle} from 'react-icons/all'
import {fileUrl} from '../../redux/api/fileApi'

const Image = ({image, setSelectedImage}) => {
  const closeDialog = () => {
    setSelectedImage(null)
  }

  return (
    <div className="modal show fade" style={{display: 'block'}} onClick={closeDialog} onContextMenu={closeDialog}>
      <div className="modal-fullscreen">
        <div className="modal-content" style={{background: 'black', color: '#BEBEBE'}}>
          <div className="modal-body row p-5">
            <div className="col-9" style={{
              background: `url(${fileUrl}/download/${image.path})`,
              backgroundSize: 'contain',
              backgroundRepeat: 'no-repeat',
              backgroundPosition: 'center',
              height: '100%'
            }}/>
            <div className="col-3 p-4">
              <div className="text-end">
                <AiOutlineCloseCircle size="2em"/>
              </div>
              <h2>{image.author}</h2>
              <div className="text-end"
                   style={{fontSize: 'x-small'}}>{moment(image.uploaded).format('YYYY-MM-DD hh:mm')}</div>
              <p className="my-5">{image.description}</p>
              <ImageActionButton type={image.ownershipType} size="2em"/>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

Image.propType = {
  setSelectedImage: PropTypes.func,
  image: PropTypes.shape({
    path: PropTypes.string.isRequired,
    author: PropTypes.string.isRequired,
    uploaded: PropTypes.string.isRequired,
    description: PropTypes.string,
    ownershipType: PropTypes.string,
    hashTags: PropTypes.array
  })
}

export default Image