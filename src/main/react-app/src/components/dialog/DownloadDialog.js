import {
  Button,
  Col,
  DropdownButton,
  ListGroup,
  ModalBody,
  ModalDialog,
  ModalFooter,
  ModalTitle,
  Row
} from 'react-bootstrap'
import ModalHeader from 'react-bootstrap/ModalHeader'
import PropTypes from 'prop-types'
import {useDispatch, useSelector} from 'react-redux'
import {hideDialog} from '../../redux/slice/currentDialogSlice'
import {useState} from 'react'
import {BsImage} from 'react-icons/bs'
import {saveAs} from 'file-saver'
import {baseUrl} from '../../redux/api/baseApi'
import DropdownItem from 'react-bootstrap/DropdownItem'
import {selectCurrentUser} from '../../redux/slice/currentUserSlice'

const DownloadDialog = ({image}) => {
  const currentUser = useSelector(selectCurrentUser)
  const dispatch = useDispatch()
  const [imageSize, setImageSize] = useState('BIG')
  const [filter, setFilter] = useState(null)

  const download = () => {
    const auth = currentUser.user?.token || ''
    const url = `${baseUrl}/photo/${image.path}?auth=${encodeURI(auth)}&size=${imageSize}` + (!filter ? '' : `&filter=${filter}`)
    saveAs(url, image.title + image.path.substring(image.path.lastIndexOf('.')))
    dispatch(hideDialog(null))
  }

  const cancel = () => {
    dispatch(hideDialog(null))
  }

  const uppercaseOnlyFirstLetter = (word) => {
    return word.charAt(0) + word.slice(1).toLowerCase()
  }

  return (
    <div className="position-fixed top-0 bottom-0 w-100 dialog-box">
      <ModalDialog centered={true}>
        <ModalHeader>
          <ModalTitle>Download image</ModalTitle>
        </ModalHeader>
        <ModalBody>
          <Row>
            <Col>Image size:</Col>
            <Col>
              <ListGroup horizontal>
                <ListGroup.Item className="switcher-item text-center"
                                data-selected={imageSize === 'BIG'}
                                onClick={() => setImageSize('BIG')}>
                  <BsImage style={{fontSize: '1.3em'}}/>
                </ListGroup.Item>
                <ListGroup.Item className="switcher-item text-center"
                                data-selected={imageSize === 'SMALL'}
                                onClick={() => setImageSize('SMALL')}>
                  <BsImage style={{fontSize: '1em'}}/>
                </ListGroup.Item>
              </ListGroup>
            </Col>
          </Row>
          <Row className="mt-3">
            <Col>Filter:</Col>
            <Col>
              <DropdownButton className="dropdown w-100" variant="flat"
                              title={filter === null ? 'None' : uppercaseOnlyFirstLetter(filter)}>
                <DropdownItem onClick={() => setFilter(null)}>None</DropdownItem>
                <DropdownItem onClick={() => setFilter('GREYSCALE')}>Grayscale</DropdownItem>
                <DropdownItem onClick={() => setFilter('INVERT')}>Invert</DropdownItem>
                <DropdownItem onClick={() => setFilter('SEPIA')}>Sepia</DropdownItem>
              </DropdownButton>
            </Col>
          </Row>
        </ModalBody>
        <ModalFooter>
          <Button className="button-dark" onClick={cancel}>Cancel</Button>
          <Button className="button-dark" onClick={download}>Download</Button>
        </ModalFooter>
      </ModalDialog>
    </div>
  )
}

DownloadDialog.propTypes = {
  image: PropTypes.shape({
    path: PropTypes.string.isRequired,
    title: PropTypes.string.isRequired
  }).isRequired
}

export default DownloadDialog
